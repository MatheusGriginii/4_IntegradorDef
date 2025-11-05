package app.projeto.repository;

import app.projeto.entity.Usuario;
import app.projeto.entity.Usuario.PerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuário por email (usado para login)
    Optional<Usuario> findByEmail(String email);

    // Buscar usuário por email e ativo
    Optional<Usuario> findByEmailAndAtivo(String email, Boolean ativo);

    // Buscar usuários por perfil
    List<Usuario> findByPerfil(PerfilUsuario perfil);

    // Buscar usuários ativos
    List<Usuario> findByAtivoTrue();

    // Buscar usuários por perfil e status ativo
    List<Usuario> findByPerfilAndAtivoTrue(PerfilUsuario perfil);

    // Verificar se email já existe
    boolean existsByEmail(String email);

    // Verificar se email já existe para outro usuário (útil para edição)
    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.email = :email AND u.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);

    // Buscar por nome contendo (para pesquisa)
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND u.ativo = true")
    List<Usuario> findByNomeContainingIgnoreCaseAndAtivoTrue(@Param("nome") String nome);

    // Buscar administradores ativos
    @Query("SELECT u FROM Usuario u WHERE u.perfil = 'ADMIN' AND u.ativo = true")
    List<Usuario> findAdministradoresAtivos();

    // Buscar funcionários ativos
    @Query("SELECT u FROM Usuario u WHERE u.perfil IN ('FUNCIONARIO', 'GERENTE') AND u.ativo = true")
    List<Usuario> findFuncionariosAtivos();

    // Contar usuários por perfil
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.perfil = :perfil AND u.ativo = true")
    Long countByPerfilAndAtivoTrue(@Param("perfil") PerfilUsuario perfil);

    // Buscar usuários criados recentemente (últimos 30 dias)
    @Query("SELECT u FROM Usuario u WHERE u.dataCriacao >= :dataLimite AND u.ativo = true ORDER BY u.dataCriacao DESC")
    List<Usuario> findUsuariosRecentesAtivos(@Param("dataLimite") LocalDateTime dataLimite);

    // Método default para facilitar o uso
    default List<Usuario> findUsuariosRecentesAtivos() {
        return findUsuariosRecentesAtivos(LocalDateTime.now().minusDays(30));
    }
}
