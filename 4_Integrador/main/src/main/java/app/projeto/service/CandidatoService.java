package app.projeto.service;

import app.projeto.entity.Candidato;
import app.projeto.repository.CandidatoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatoService {
    private final CandidatoRepository candidatoRepository;

    public CandidatoService(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository;
    }

    public List<Candidato> listar() {
        return candidatoRepository.findAll();
    }

    public Candidato buscarPorId(Long id) {
        return candidatoRepository.findById(id).orElseThrow(() -> new RuntimeException("Candidato não encontrado"));
    }

    public List<Candidato> buscarPorNome(String nome) {
        return candidatoRepository.findByNomeIgnoreCaseContaining(nome);
    }

    public List<Candidato> buscarPorTituloVaga(String titulo) {
        return candidatoRepository.findByVagasTitulo(titulo);
    }

    public Candidato criar(Candidato candidato) {
        if (candidato.getNome() == null || candidato.getNome().isBlank()) {
            throw new RuntimeException("O campo nome do candidato é obrigatório");
        }
        return candidatoRepository.save(candidato);
    }

    public Candidato atualizar(Long id, Candidato candidato) {
        Candidato existente = buscarPorId(id);
        existente.setNome(candidato.getNome());
        existente.setVagas(candidato.getVagas());
        return candidatoRepository.save(existente);
    }

    public void deletar(Long id) {
        candidatoRepository.deleteById(id);
    }
}
