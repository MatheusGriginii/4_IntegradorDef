package app.projeto.repository;

import app.projeto.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
    List<Vaga> findByTituloIgnoreCaseContaining(String titulo);
    List<Vaga> findByEmpresaNome(String nome);

    @Query("SELECT v FROM Vaga v WHERE v.titulo LIKE %:titulo% AND v.empresa.nome = :empresaNome")
    List<Vaga> buscarVagasPorTituloEEmpresa(@Param("titulo") String titulo, @Param("empresaNome") String empresaNome);
}
