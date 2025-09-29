package app.projeto.service;

import app.projeto.entity.Vaga;
import app.projeto.repository.VagaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VagaService {
    private final VagaRepository vagaRepository;

    public VagaService(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    public List<Vaga> listar() {
        return vagaRepository.findAll();
    }

    public Vaga buscarPorId(Long id) {
        return vagaRepository.findById(id).orElseThrow(() -> new RuntimeException("Vaga não encontrado"));
    }

    public List<Vaga> buscarPorTitulo(String titulo) {
        return vagaRepository.findByTituloIgnoreCaseContaining(titulo);
    }

    public List<Vaga> buscarPorEmpresa(String empresaNome) {
        return vagaRepository.findByEmpresaNome(empresaNome);
    }

    public Vaga criar(Vaga vaga) {
        if (vaga.getEmpresa() == null) {
            throw new RuntimeException("Não é possível cadastrar vaga sem empresa associada.");
        }
        if (vaga.getTitulo() != null) {
            vaga.setTitulo(vaga.getTitulo().trim());
        }
        return vagaRepository.save(vaga);
    }

    public Vaga atualizar(Long id, Vaga vaga) {
        Vaga existente = buscarPorId(id);
        existente.setTitulo(vaga.getTitulo());
        existente.setEmpresa(vaga.getEmpresa());
        existente.setCandidatos(vaga.getCandidatos());
        existente.setUsuarios(vaga.getUsuarios());
        if (existente.getTitulo() != null) {
            existente.setTitulo(existente.getTitulo().trim());
        }
        return vagaRepository.save(existente);
    }

    public void deletar(Long id) {
        vagaRepository.deleteById(id);
    }
}
