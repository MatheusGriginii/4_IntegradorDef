package app.projeto.repository;

import app.projeto.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    List<Empresa> findByNomeIgnoreCaseContaining(String nome);
    List<Empresa> findByVagasTitulo(String titulo);
}
