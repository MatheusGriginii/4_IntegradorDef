package app.projeto.service;

import app.projeto.entity.Categoria;
import app.projeto.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Categoria> listarAtivas() {
        return categoriaRepository.findByAtivaTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Categoria> buscarPorNome(String nome) {
        return categoriaRepository.findByNomeIgnoreCase(nome);
    }

    @Transactional(readOnly = true)
    public List<Categoria> buscarPorNomeContendo(String nome) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional(readOnly = true)
    public boolean existePorNome(String nome) {
        return categoriaRepository.existsByNomeIgnoreCase(nome);
    }

    @Transactional
    public Categoria salvar(Categoria categoria) {
        // Só verifica duplicidade se a categoria for nova (sem ID)
        if (categoria.getId() == null && existePorNome(categoria.getNome())) {
            throw new RuntimeException("Já existe uma categoria com este nome");
        }
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria atualizar(Long id, Categoria categoriaAtualizada) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // Verifica se o novo nome já existe em outra categoria
        if (!categoriaExistente.getNome().equalsIgnoreCase(categoriaAtualizada.getNome())) {
            if (existePorNome(categoriaAtualizada.getNome())) {
                throw new RuntimeException("Já existe uma categoria com este nome");
            }
        }

        categoriaExistente.setNome(categoriaAtualizada.getNome());
        categoriaExistente.setDescricao(categoriaAtualizada.getDescricao());
        categoriaExistente.setAtiva(categoriaAtualizada.getAtiva());
        categoriaExistente.setIcone(categoriaAtualizada.getIcone());
        categoriaExistente.setCor(categoriaAtualizada.getCor());

        return categoriaRepository.save(categoriaExistente);
    }

    @Transactional
    public void deletar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        
        // Verifica se há produtos vinculados
        if (!categoria.getProdutos().isEmpty()) {
            throw new RuntimeException("Não é possível deletar categoria com produtos vinculados");
        }
        
        categoriaRepository.delete(categoria);
    }

    @Transactional
    public void desativar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        categoria.setAtiva(false);
        categoriaRepository.save(categoria);
    }

    @Transactional
    public void ativar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        categoria.setAtiva(true);
        categoriaRepository.save(categoria);
    }

    @Transactional(readOnly = true)
    public List<Categoria> listarComProdutos() {
        return categoriaRepository.findAll().stream()
                .filter(c -> !c.getProdutos().isEmpty())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Categoria> listarSemProdutos() {
        return categoriaRepository.findAll().stream()
                .filter(c -> c.getProdutos().isEmpty())
                .toList();
    }
}
