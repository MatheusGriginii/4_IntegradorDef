package app.projeto.service;

import app.projeto.entity.Usuario;
import app.projeto.entity.Usuario.PerfilUsuario;
import app.projeto.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Buscar todos os usuários
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Buscar apenas usuários ativos
    public List<Usuario> listarAtivos() {
        return usuarioRepository.findByAtivoTrue();
    }

    // Buscar usuário por ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

    // Buscar usuário por email (para login)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Buscar usuário por email e ativo (para login)
    public Optional<Usuario> buscarPorEmailAtivo(String email) {
        return usuarioRepository.findByEmailAndAtivo(email, true);
    }

    // Buscar usuários por nome
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome);
    }

    // Buscar usuários por perfil
    public List<Usuario> buscarPorPerfil(PerfilUsuario perfil) {
        return usuarioRepository.findByPerfilAndAtivoTrue(perfil);
    }

    // Buscar administradores
    public List<Usuario> buscarAdministradores() {
        return usuarioRepository.findAdministradoresAtivos();
    }

    // Buscar funcionários
    public List<Usuario> buscarFuncionarios() {
        return usuarioRepository.findFuncionariosAtivos();
    }

    // Criar novo usuário
    public Usuario criar(Usuario usuario) {
        // Validações
        validarUsuario(usuario, true);
        
        // Verificar se email já existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Já existe um usuário com este email: " + usuario.getEmail());
        }

        // Criptografar senha
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        
        // Normalizar dados
        usuario.setNome(usuario.getNome().trim());
        usuario.setEmail(usuario.getEmail().toLowerCase().trim());
        
        return usuarioRepository.save(usuario);
    }

    // Atualizar usuário
    public Usuario atualizar(Long id, Usuario usuario) {
        Usuario existente = buscarPorId(id);
        
        // Validações
        validarUsuario(usuario, false);
        
        // Verificar se email já existe para outro usuário
        if (usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), id)) {
            throw new RuntimeException("Já existe outro usuário com este email: " + usuario.getEmail());
        }

        // Atualizar campos
        existente.setNome(usuario.getNome().trim());
        existente.setEmail(usuario.getEmail().toLowerCase().trim());
        existente.setPerfil(usuario.getPerfil());
        existente.setAtivo(usuario.getAtivo());
        
        // Atualizar senha apenas se fornecida
        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            existente.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        
        return usuarioRepository.save(existente);
    }

    // Ativar/Desativar usuário
    public Usuario alterarStatus(Long id, Boolean ativo) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(ativo);
        return usuarioRepository.save(usuario);
    }

    // Alterar senha
    public Usuario alterarSenha(Long id, String novaSenha) {
        if (novaSenha == null || novaSenha.length() < 6) {
            throw new RuntimeException("A nova senha deve ter no mínimo 6 caracteres");
        }
        
        Usuario usuario = buscarPorId(id);
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        return usuarioRepository.save(usuario);
    }

    // Deletar usuário (soft delete)
    public void deletar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    // Deletar permanentemente
    public void deletarPermanentemente(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Verificar senha
    public boolean verificarSenha(String senhaPlana, String senhaHash) {
        return passwordEncoder.matches(senhaPlana, senhaHash);
    }

    // Contar usuários por perfil
    public Long contarPorPerfil(PerfilUsuario perfil) {
        return usuarioRepository.countByPerfilAndAtivoTrue(perfil);
    }

    // Buscar usuários recentes
    public List<Usuario> buscarUsuariosRecentes() {
        return usuarioRepository.findUsuariosRecentesAtivos();
    }

    // Validação de usuário
    private void validarUsuario(Usuario usuario, boolean isNew) {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new RuntimeException("O nome é obrigatório");
        }
        
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new RuntimeException("O email é obrigatório");
        }
        
        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new RuntimeException("Email deve ter um formato válido");
        }
        
        if (isNew && (usuario.getSenha() == null || usuario.getSenha().length() < 6)) {
            throw new RuntimeException("A senha deve ter no mínimo 6 caracteres");
        }
        
        if (usuario.getPerfil() == null) {
            throw new RuntimeException("O perfil é obrigatório");
        }
    }
}
