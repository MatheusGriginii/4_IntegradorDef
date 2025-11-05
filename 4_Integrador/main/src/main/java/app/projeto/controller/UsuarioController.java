package app.projeto.controller;

import app.projeto.entity.Usuario;
import app.projeto.entity.Usuario.PerfilUsuario;
import app.projeto.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Para desenvolvimento - configurar adequadamente em produção
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // Listar apenas usuários ativos
    @GetMapping("/ativos")
    public ResponseEntity<List<Usuario>> listarAtivos() {
        List<Usuario> usuarios = usuarioService.listarAtivos();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar usuários por nome
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam String nome) {
        List<Usuario> usuarios = usuarioService.buscarPorNome(nome);
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuários por perfil
    @GetMapping("/perfil/{perfil}")
    public ResponseEntity<List<Usuario>> buscarPorPerfil(@PathVariable PerfilUsuario perfil) {
        List<Usuario> usuarios = usuarioService.buscarPorPerfil(perfil);
        return ResponseEntity.ok(usuarios);
    }

    // Buscar administradores
    @GetMapping("/administradores")
    public ResponseEntity<List<Usuario>> buscarAdministradores() {
        List<Usuario> usuarios = usuarioService.buscarAdministradores();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar funcionários
    @GetMapping("/funcionarios")
    public ResponseEntity<List<Usuario>> buscarFuncionarios() {
        List<Usuario> usuarios = usuarioService.buscarFuncionarios();
        return ResponseEntity.ok(usuarios);
    }

    // Criar novo usuário
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.criar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // Ativar/Desativar usuário
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> status) {
        try {
            Boolean ativo = status.get("ativo");
            Usuario usuario = usuarioService.alterarStatus(id, ativo);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // Alterar senha
    @PatchMapping("/{id}/senha")
    public ResponseEntity<?> alterarSenha(@PathVariable Long id, @RequestBody Map<String, String> dados) {
        try {
            String novaSenha = dados.get("novaSenha");
            usuarioService.alterarSenha(id, novaSenha);
            return ResponseEntity.ok(Map.of("mensagem", "Senha alterada com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // Deletar usuário (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            usuarioService.deletar(id);
            return ResponseEntity.ok(Map.of("mensagem", "Usuário desativado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // Deletar permanentemente
    @DeleteMapping("/{id}/permanente")
    public ResponseEntity<?> deletarPermanentemente(@PathVariable Long id) {
        try {
            usuarioService.deletarPermanentemente(id);
            return ResponseEntity.ok(Map.of("mensagem", "Usuário removido permanentemente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // Estatísticas de usuários
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Long>> obterEstatisticas() {
        Long totalAdmins = usuarioService.contarPorPerfil(PerfilUsuario.ADMIN);
        Long totalFuncionarios = usuarioService.contarPorPerfil(PerfilUsuario.FUNCIONARIO);
        Long totalGerentes = usuarioService.contarPorPerfil(PerfilUsuario.GERENTE);
        
        Map<String, Long> estatisticas = Map.of(
            "administradores", totalAdmins,
            "funcionarios", totalFuncionarios,
            "gerentes", totalGerentes,
            "total", totalAdmins + totalFuncionarios + totalGerentes
        );
        
        return ResponseEntity.ok(estatisticas);
    }

    // Buscar usuários recentes
    @GetMapping("/recentes")
    public ResponseEntity<List<Usuario>> buscarUsuariosRecentes() {
        List<Usuario> usuarios = usuarioService.buscarUsuariosRecentes();
        return ResponseEntity.ok(usuarios);
    }
}
