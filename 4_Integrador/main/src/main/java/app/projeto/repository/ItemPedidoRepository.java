package app.projeto.repository;

import app.projeto.entity.ItemPedido;
import app.projeto.entity.Pedido;
import app.projeto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    
    // Buscar itens por pedido
    List<ItemPedido> findByPedido(Pedido pedido);
    
    List<ItemPedido> findByPedidoId(Long pedidoId);
    
    // Buscar itens por produto
    List<ItemPedido> findByProduto(Produto produto);
    
    List<ItemPedido> findByProdutoId(Long produtoId);
    
    // Buscar itens por pedido e produto
    List<ItemPedido> findByPedidoIdAndProdutoId(Long pedidoId, Long produtoId);
    
    // Contar itens de um pedido
    Long countByPedidoId(Long pedidoId);
    
    // Produtos mais vendidos
    @Query("SELECT i.produto, SUM(i.quantidade) as total FROM ItemPedido i " +
           "JOIN i.pedido p " +
           "WHERE p.status != 'CANCELADO' " +
           "GROUP BY i.produto " +
           "ORDER BY total DESC")
    List<Object[]> findProdutosMaisVendidos();
    
    // Produtos mais vendidos com limite
    @Query("SELECT i.produto, SUM(i.quantidade) as total FROM ItemPedido i " +
           "JOIN i.pedido p " +
           "WHERE p.status != 'CANCELADO' " +
           "GROUP BY i.produto " +
           "ORDER BY total DESC " +
           "LIMIT :limite")
    List<Object[]> findTopProdutosMaisVendidos(@Param("limite") int limite);
    
    // Total de unidades vendidas de um produto
    @Query("SELECT COALESCE(SUM(i.quantidade), 0) FROM ItemPedido i " +
           "JOIN i.pedido p " +
           "WHERE i.produto.id = :produtoId AND p.status != 'CANCELADO'")
    Long calcularTotalVendidoProduto(@Param("produtoId") Long produtoId);
    
    // Deletar itens de um pedido
    void deleteByPedidoId(Long pedidoId);
}
