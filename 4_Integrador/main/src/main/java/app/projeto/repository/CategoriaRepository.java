package app.projeto.repository;

import app.projeto.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    // Buscar categorias ativas
    List<Categoria> findByAtivaTrue();
    
    Page<Categoria> findByAtivaTrue(Pageable pageable);
    
    // Buscar categoria por nome (case insensitive)
    Optional<Categoria> findByNomeIgnoreCaseAndAtivaTrue(String nome);
    
    // Buscar categoria por nome (qualquer status)
    Optional<Categoria> findByNomeIgnoreCase(String nome);
    
    // Buscar categorias por nome contendo texto (case insensitive)
    @Query("SELECT c FROM Categoria c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND c.ativa = true")
    List<Categoria> findByNomeContainingIgnoreCaseAndAtivaTrue(@Param("nome") String nome);
    
    // Buscar categorias por nome contendo texto (qualquer status)
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
    
    Page<Categoria> findByNomeContainingIgnoreCaseAndAtivaTrue(String nome, Pageable pageable);
    
    // Verificar se categoria existe por nome (qualquer status)
    boolean existsByNomeIgnoreCase(String nome);
    
    // Buscar categorias ordenadas por nome
    List<Categoria> findByAtivaTrueOrderByNomeAsc();
    
    // Buscar categorias ordenadas por data de criação
    List<Categoria> findByAtivaTrueOrderByDataCriacaoDesc();
    
    // Buscar categorias que têm produtos
    @Query("SELECT DISTINCT c FROM Categoria c JOIN c.produtos p WHERE c.ativa = true AND p.ativo = true")
    List<Categoria> findCategoriasComProdutos();
    
    // Buscar categorias sem produtos
    @Query("SELECT c FROM Categoria c WHERE c.ativa = true AND " +
           "(c.produtos IS EMPTY OR NOT EXISTS (SELECT p FROM Produto p WHERE p.categoria = c AND p.ativo = true))")
    List<Categoria> findCategoriasSemProdutos();
    
    // Contar total de categorias ativas
    @Query("SELECT COUNT(c) FROM Categoria c WHERE c.ativa = true")
    Long countCategoriasAtivas();
    
    // Verificar se categoria existe por nome (para evitar duplicatas)
    boolean existsByNomeIgnoreCaseAndAtivaTrue(String nome);
    
    // Buscar categoria com maior número de produtos
    @Query(value = """
            SELECT c.* FROM categorias c 
            JOIN produtos p ON c.id = p.categoria_id 
            WHERE c.categoria_ativa = true AND p.produto_ativo = true 
            GROUP BY c.id 
            ORDER BY COUNT(p.id) DESC 
            LIMIT 1
            """, nativeQuery = true)
    Optional<Categoria> findCategoriaComMaisProdutos();
    
    // Buscar categorias com contagem de produtos
    @Query("SELECT c, COUNT(p) as totalProdutos FROM Categoria c " +
           "LEFT JOIN c.produtos p ON p.ativo = true " +
           "WHERE c.ativa = true " +
           "GROUP BY c.id " +
           "ORDER BY totalProdutos DESC")
    List<Object[]> findCategoriasComContagemProdutos();
    
    // Buscar por descrição
    @Query("SELECT c FROM Categoria c WHERE LOWER(c.descricao) LIKE LOWER(CONCAT('%', :descricao, '%')) AND c.ativa = true")
    List<Categoria> findByDescricaoContainingIgnoreCaseAndAtivaTrue(@Param("descricao") String descricao);
    
    // Buscar categorias por cor
    List<Categoria> findByCorAndAtivaTrue(String cor);
    
    // Buscar categorias por ícone
    List<Categoria> findByIconeAndAtivaTrue(String icone);
    
    // Buscar todas as categorias (incluindo inativas) para administração
    List<Categoria> findAllByOrderByDataCriacaoDesc();
    
    // Buscar categorias por status
    List<Categoria> findByAtiva(Boolean ativa);
    
    Page<Categoria> findByAtiva(Boolean ativa, Pageable pageable);
    
    // Buscar categoria por ID se estiver ativa
    @Query("SELECT c FROM Categoria c WHERE c.id = :id AND c.ativa = true")
    Optional<Categoria> findByIdAndAtivaTrue(@Param("id") Long id);
}