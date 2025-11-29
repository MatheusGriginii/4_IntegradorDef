package app.projeto.controller;

import app.projeto.config.JwtUtil;
import app.projeto.dto.LoginRequest;
import app.projeto.dto.LoginResponse;
import app.projeto.dto.RegisterRequest;
import app.projeto.entity.Usuario;
import app.projeto.security.CustomUserDetailsService;
import app.projeto.service.UsuarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(AuthenticationManager authenticationManager,
                         JwtUtil jwtUtil,
                         UsuarioService usuarioService,
                         CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Endpoint de login - valida credenciais e retorna JWT
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("=== TENTATIVA DE LOGIN ===");
        logger.info("Email recebido: {}", loginRequest.getEmail());
        logger.info("Senha recebida (length): {}", loginRequest.getSenha() != null ? loginRequest.getSenha().length() : 0);
        
        try {
            // Autenticar o usuário
            logger.info("Iniciando autenticação...");
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getSenha()
                )
            );
            
            logger.info("Autenticação bem-sucedida!");
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Gerar token JWT
            String jwt = jwtUtil.gerarToken(loginRequest.getEmail());
            logger.info("Token JWT gerado");

            // Buscar informações do usuário
            Usuario usuario = customUserDetailsService.carregarUsuarioPorEmail(loginRequest.getEmail());
            logger.info("Usuário carregado: {} - Perfil: {}", usuario.getNome(), usuario.getPerfil());

            // Retornar resposta com token e dados do usuário
            LoginResponse response = new LoginResponse(
                jwt,
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil()
            );

            logger.info("Login realizado com sucesso!");
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            logger.error("Falha na autenticação: {}", e.getMessage());
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("erro", "Email ou senha inválidos"));
        } catch (Exception e) {
            logger.error("Erro ao processar login: {}", e.getMessage(), e);
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("erro", "Erro ao processar login: " + e.getMessage()));
        }
    }

    /**
     * Endpoint de registro - cria novo usuário
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(registerRequest.getNome());
            usuario.setEmail(registerRequest.getEmail());
            usuario.setSenha(registerRequest.getSenha());
            usuario.setPerfil(registerRequest.getPerfil());
            usuario.setAtivo(true);

            Usuario novoUsuario = usuarioService.criar(usuario);

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                    "mensagem", "Usuário registrado com sucesso",
                    "id", novoUsuario.getId(),
                    "email", novoUsuario.getEmail()
                ));

        } catch (RuntimeException e) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("erro", "Erro ao registrar usuário: " + e.getMessage()));
        }
    }

    /**
     * Endpoint para validar token
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                
                if (jwtUtil.validarToken(token)) {
                    String email = jwtUtil.getEmailDoToken(token);
                    Usuario usuario = customUserDetailsService.carregarUsuarioPorEmail(email);
                    
                    return ResponseEntity.ok(Map.of(
                        "valido", true,
                        "email", usuario.getEmail(),
                        "nome", usuario.getNome(),
                        "perfil", usuario.getPerfil()
                    ));
                }
            }
            
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("valido", false, "erro", "Token inválido"));
                
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("valido", false, "erro", "Token inválido"));
        }
    }

    /**
     * Endpoint de logout (cliente deve descartar o token)
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("mensagem", "Logout realizado com sucesso"));
    }
}
