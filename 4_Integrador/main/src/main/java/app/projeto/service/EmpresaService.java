package app.projeto.service;

import app.projeto.entity.Empresa;
import app.projeto.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> listar() {
        return empresaRepository.findAll();
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
    }

    public List<Empresa> buscarPorNome(String nome) {
        return empresaRepository.findByNomeIgnoreCaseContaining(nome);
    }

    public List<Empresa> buscarPorTituloVaga(String titulo) {
        return empresaRepository.findByVagasTitulo(titulo);
    }

    public Empresa criar(Empresa empresa) {
        if (empresa.getNome() == null || empresa.getNome().isBlank()) {
            throw new RuntimeException("Nome da empresa é obrigatório");
        }
        empresa.setNome(empresa.getNome().trim());
        return empresaRepository.save(empresa);
    }

    public Empresa atualizar(Long id, Empresa empresa) {
        Empresa existente = buscarPorId(id);
        if (empresa.getNome() == null || empresa.getNome().isBlank()) {
            throw new RuntimeException("Nome da empresa é obrigatório");
        }
        existente.setNome(empresa.getNome().trim());
        return empresaRepository.save(existente);
    }

    public void deletar(Long id) {
        empresaRepository.deleteById(id);
    }
}
