package app.projeto.repository;

import app.projeto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNomeIgnoreCaseContaining(String nome);
    List<Usuario> findByEnderecoCidade(String cidade);

    @Query("SELECT u FROM Usuario u WHERE u.nome = :nome AND u.endereco.cidade = :cidade")
    List<Usuario> buscaPorNomeECidade(@Param("nome") String nome, @Param("cidade") String cidade);
}
