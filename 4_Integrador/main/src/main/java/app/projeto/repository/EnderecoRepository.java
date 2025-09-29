package app.projeto.repository;

import app.projeto.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByCidadeIgnoreCaseContaining(String cidade);
    List<Endereco> findByEstadoIgnoreCaseContaining(String estado);
}
