package app.projeto.repository;

import app.projeto.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    List<Candidato> findByNomeIgnoreCaseContaining(String nome);
    List<Candidato> findByVagasTitulo(String titulo);
}
