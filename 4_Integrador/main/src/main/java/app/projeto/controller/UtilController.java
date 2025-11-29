package app.projeto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/util")
@CrossOrigin(origins = "*")
public class UtilController {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * ENDPOINT TEMPORÁRIO - Gera hash BCrypt de uma senha
     * Use apenas para desenvolvimento!
     * DELETE ESTE ARQUIVO DEPOIS!
     */
    @GetMapping("/gerar-hash/{senha}")
    public ResponseEntity<?> gerarHash(@PathVariable String senha) {
        String hash = passwordEncoder.encode(senha);
        
        return ResponseEntity.ok(Map.of(
            "senha_original", senha,
            "hash_bcrypt", hash,
            "instrucao", "Execute no EC2: UPDATE usuarios SET senha = '" + hash + "' WHERE email = 'admin@adorela.com.br';"
        ));
    }

    /**
     * ENDPOINT TEMPORÁRIO - Testa se uma senha bate com um hash
     */
    @PostMapping("/testar-senha")
    public ResponseEntity<?> testarSenha(@RequestBody Map<String, String> dados) {
        String senhaPlana = dados.get("senha");
        String hash = dados.get("hash");
        
        boolean matches = passwordEncoder.matches(senhaPlana, hash);
        
        return ResponseEntity.ok(Map.of(
            "senha", senhaPlana,
            "hash", hash,
            "match", matches,
            "resultado", matches ? "✅ SENHA CORRETA!" : "❌ Senha incorreta"
        ));
    }
}
