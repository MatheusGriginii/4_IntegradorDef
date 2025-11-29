package app.projeto.repository;

import app.projeto.entity.Produto;
import app.projeto.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    // Buscar produtos ativos
    List<Produto> findByAtivoTrue();
    
    Page<Produto> findByAtivoTrue(Pageable pageable);
    
    // Contar produtos ativos
    long countByAtivoTrue();
    
    // Buscar por categoria
    List<Produto> findByCategoriaAndAtivoTrue(Categoria categoria);
    
    Page<Produto> findByCategoriaAndAtivoTrue(Categoria categoria, Pageable pageable);
    
    // Buscar por categoria ID
    List<Produto> findByCategoriaIdAndAtivoTrue(Long categoriaId);
    
    Page<Produto> findByCategoriaIdAndAtivoTrue(Long categoriaId, Pageable pageable);
    
    // Buscar por nome (case insensitive)
    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND p.ativo = true")
    List<Produto> findByNomeContainingIgnoreCaseAndAtivoTrue(@Param("nome") String nome);
    
    Page<Produto> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome, Pageable pageable);
    
    // Buscar por nome (qualquer produto, ativo ou inativo)
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    
    // Buscar por categoria ID (qualquer produto, ativo ou inativo)
    List<Produto> findByCategoriaId(Long categoriaId);
    
    // Buscar por faixa de preço
    @Query("SELECT p FROM Produto p WHERE p.preco BETWEEN :precoMin AND :precoMax AND p.ativo = true")
    List<Produto> findByPrecoRange(@Param("precoMin") BigDecimal precoMin, 
                                  @Param("precoMax") BigDecimal precoMax);
    
    Page<Produto> findByPrecoBetweenAndAtivoTrue(BigDecimal precoMin, BigDecimal precoMax, Pageable pageable);
    
    // Buscar produtos com estoque baixo
    @Query("SELECT p FROM Produto p WHERE p.estoque <= p.estoqueMinimo AND p.ativo = true")
    List<Produto> findProdutosComEstoqueBaixo();
    
    // Buscar produtos sem estoque
    List<Produto> findByEstoqueAndAtivoTrue(Integer estoque);
    
    @Query("SELECT p FROM Produto p WHERE p.estoque = 0 AND p.ativo = true")
    List<Produto> findProdutosSemEstoque();
    
    // Buscar produtos por categoria e nome
    @Query("SELECT p FROM Produto p WHERE p.categoria.id = :categoriaId AND " +
           "LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND p.ativo = true")
    List<Produto> findByCategoriaIdAndNome(@Param("categoriaId") Long categoriaId, 
                                          @Param("nome") String nome);
    
    Page<Produto> findByCategoriaIdAndNomeContainingIgnoreCaseAndAtivoTrue(
            Long categoriaId, String nome, Pageable pageable);
    
    // Produtos mais vendidos (simulando baseado no ID - em um sistema real seria por vendas)
    @Query(value = "SELECT * FROM produtos p WHERE p.produto_ativo = true ORDER BY p.id ASC LIMIT :limit", 
           nativeQuery = true)
    List<Produto> findProdutosMaisVendidos(@Param("limit") int limit);
    
    // Produtos em destaque (últimos adicionados)
    @Query("SELECT p FROM Produto p WHERE p.ativo = true ORDER BY p.dataCriacao DESC")
    List<Produto> findProdutosEmDestaque(Pageable pageable);
    
    // Buscar por ingredientes
    @Query("SELECT p FROM Produto p WHERE LOWER(p.ingredientes) LIKE LOWER(CONCAT('%', :ingrediente, '%')) AND p.ativo = true")
    List<Produto> findByIngredientesContaining(@Param("ingrediente") String ingrediente);
    
    // Produtos por tempo de preparo
    List<Produto> findByTempoPreparoMinutosLessThanEqualAndAtivoTrue(Integer tempoMaximo);
    
    // Contar produtos por categoria
    @Query("SELECT COUNT(p) FROM Produto p WHERE p.categoria.id = :categoriaId AND p.ativo = true")
    Long countByCategoriaId(@Param("categoriaId") Long categoriaId);
    
    // Valor total do estoque
    @Query("SELECT SUM(p.preco * p.estoque) FROM Produto p WHERE p.ativo = true")
    BigDecimal calcularValorTotalEstoque();
    
    // Buscar produtos ordenados por preço
    List<Produto> findByAtivoTrueOrderByPrecoAsc();
    List<Produto> findByAtivoTrueOrderByPrecoDesc();
    
    // Buscar produtos ordenados por nome
    List<Produto> findByAtivoTrueOrderByNomeAsc();
    
    // Buscar produtos ordenados por data de criação
    List<Produto> findByAtivoTrueOrderByDataCriacaoDesc();
    
    // Verificar se produto existe por nome (para evitar duplicatas)
    boolean existsByNomeIgnoreCaseAndAtivoTrue(String nome);
    
    // Buscar produtos com preço menor que um valor
    List<Produto> findByPrecoLessThanAndAtivoTrue(BigDecimal preco);
    
    // Buscar produtos com preço maior que um valor
    List<Produto> findByPrecoGreaterThanAndAtivoTrue(BigDecimal preco);
}