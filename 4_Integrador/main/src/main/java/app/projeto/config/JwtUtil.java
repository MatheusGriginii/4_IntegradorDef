package app.projeto.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final String jwtSecret;
    private final long jwtExpiration;
    private final SecretKey key;

    public JwtUtil() {
        // Carregar variáveis do .env
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .ignoreIfMissing()
                .load();
        
        this.jwtSecret = dotenv.get("JWT_SECRET", "adorela-panificadora-secret-key-super-segura-2024-mudar-em-producao");
        this.jwtExpiration = Long.parseLong(dotenv.get("JWT_EXPIRATION", "86400000")); // 24 horas padrão
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gera um token JWT a partir do email do usuário
     */
    public String gerarToken(String email) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(email)
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(key)
                .compact();
    }

    /**
     * Gera um token JWT a partir da autenticação
     */
    public String gerarTokenDeAutenticacao(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return gerarToken(userDetails.getUsername());
    }

    /**
     * Extrai o email (subject) do token JWT
     */
    public String getEmailDoToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Valida o token JWT
     */
    public boolean validarToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Token JWT inválido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Valida se o token pertence ao usuário
     */
    public boolean validarToken(String token, UserDetails userDetails) {
        try {
            String email = getEmailDoToken(token);
            return email.equals(userDetails.getUsername()) && !isTokenExpirado(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se o token está expirado
     */
    private boolean isTokenExpirado(String token) {
        try {
            Date expiracao = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            return expiracao.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Obtém a data de expiração do token
     */
    public Date getDataExpiracao(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }
}
