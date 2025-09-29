package app.projeto.service;

import app.projeto.entity.Usuario;
import app.projeto.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeIgnoreCaseContaining(nome);
    }

    public List<Usuario> buscarPorCidade(String cidade) {
        return usuarioRepository.findByEnderecoCidade(cidade);
    }

    public List<Usuario> buscarPorNomeECidade(String nome, String cidade) {
        return usuarioRepository.buscaPorNomeECidade(nome, cidade);
    }

    public Usuario criar(Usuario usuario) {
        if (usuario.getVagas() == null || usuario.getVagas().isEmpty()) {
            throw new RuntimeException("Não é possível cadastrar usuário sem associar vaga");
        }
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            usuario.setNome("INCOMPLETO");
        } else {
            usuario.setNome(usuario.getNome().trim());
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        Usuario existente = buscarPorId(id);
        existente.setNome(usuario.getNome());
        existente.setEndereco(usuario.getEndereco());
        existente.setVagas(usuario.getVagas());

        if (existente.getVagas() == null || existente.getVagas().isEmpty()) {
            throw new RuntimeException("Não é possível atualizar usuário sem associar vaga");
        }
        if (existente.getNome() == null || existente.getNome().isBlank()) {
            existente.setNome("INCOMPLETO");
        } else {
            existente.setNome(existente.getNome().trim());
        }
        return usuarioRepository.save(existente);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
