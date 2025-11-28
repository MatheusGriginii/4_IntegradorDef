package app.projeto.repository;

import app.projeto.entity.Pedido;
import app.projeto.entity.Pedido.StatusPedido;
import app.projeto.entity.Pedido.TipoPedido;
import app.projeto.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Buscar pedidos por cliente
    List<Pedido> findByCliente(Cliente cliente);
    
    List<Pedido> findByClienteId(Long clienteId);
    
    Page<Pedido> findByClienteId(Long clienteId, Pageable pageable);
    
    // Buscar pedidos por status
    List<Pedido> findByStatus(StatusPedido status);
    
    Page<Pedido> findByStatus(StatusPedido status, Pageable pageable);
    
    // Buscar pedidos por tipo
    List<Pedido> findByTipoPedido(TipoPedido tipoPedido);
    
    Page<Pedido> findByTipoPedido(TipoPedido tipoPedido, Pageable pageable);
    
    // Buscar pedidos por cliente e status
    List<Pedido> findByClienteIdAndStatus(Long clienteId, StatusPedido status);
    
    // Buscar pedidos pendentes
    @Query("SELECT p FROM Pedido p WHERE p.status = 'PENDENTE' ORDER BY p.dataPedido ASC")
    List<Pedido> findPedidosPendentes();
    
    // Buscar pedidos em preparo
    @Query("SELECT p FROM Pedido p WHERE p.status IN ('CONFIRMADO', 'PREPARANDO') ORDER BY p.dataPedido ASC")
    List<Pedido> findPedidosEmPreparo();
    
    // Buscar pedidos prontos para entrega
    @Query("SELECT p FROM Pedido p WHERE p.status = 'PRONTO' ORDER BY p.dataPedido ASC")
    List<Pedido> findPedidosProntos();
    
    // Buscar pedidos por período
    @Query("SELECT p FROM Pedido p WHERE p.dataPedido BETWEEN :dataInicio AND :dataFim ORDER BY p.dataPedido DESC")
    List<Pedido> findByPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                               @Param("dataFim") LocalDateTime dataFim);
    
    Page<Pedido> findByDataPedidoBetween(LocalDateTime dataInicio, LocalDateTime dataFim, Pageable pageable);
    
    // Buscar pedidos do dia
    @Query("SELECT p FROM Pedido p WHERE FUNCTION('DATE', p.dataPedido) = FUNCTION('CURRENT_DATE') ORDER BY p.dataPedido DESC")
    List<Pedido> findPedidosDoDia();
    
    // Buscar pedidos da semana
    @Query("SELECT p FROM Pedido p WHERE FUNCTION('WEEK', p.dataPedido) = FUNCTION('WEEK', CURRENT_TIMESTAMP) AND FUNCTION('YEAR', p.dataPedido) = FUNCTION('YEAR', CURRENT_TIMESTAMP) ORDER BY p.dataPedido DESC")
    List<Pedido> findPedidosDaSemana();
    
    // Buscar pedidos do mês
    @Query("SELECT p FROM Pedido p WHERE FUNCTION('MONTH', p.dataPedido) = FUNCTION('MONTH', CURRENT_TIMESTAMP) AND FUNCTION('YEAR', p.dataPedido) = FUNCTION('YEAR', CURRENT_TIMESTAMP) ORDER BY p.dataPedido DESC")
    List<Pedido> findPedidosDoMes();
    
    // Buscar pedidos por forma de pagamento
    List<Pedido> findByFormaPagamento(String formaPagamento);
    
    // Buscar pedidos acima de um valor
    List<Pedido> findByValorFinalGreaterThanEqual(BigDecimal valor);
    
    // Buscar pedidos por status e tipo
    List<Pedido> findByStatusAndTipoPedido(StatusPedido status, TipoPedido tipoPedido);
    
    // Contar pedidos por status
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.status = :status")
    Long countByStatus(@Param("status") StatusPedido status);
    
    // Contar pedidos por cliente
    Long countByClienteId(Long clienteId);
    
    // Valor total de vendas do dia
    @Query("SELECT COALESCE(SUM(p.valorFinal), 0) FROM Pedido p WHERE FUNCTION('DATE', p.dataPedido) = FUNCTION('CURRENT_DATE') AND p.status != 'CANCELADO'")
    BigDecimal calcularVendasDoDia();
    
    // Valor total de vendas do mês
    @Query("SELECT COALESCE(SUM(p.valorFinal), 0) FROM Pedido p WHERE FUNCTION('MONTH', p.dataPedido) = FUNCTION('MONTH', CURRENT_TIMESTAMP) AND FUNCTION('YEAR', p.dataPedido) = FUNCTION('YEAR', CURRENT_TIMESTAMP) AND p.status != 'CANCELADO'")
    BigDecimal calcularVendasDoMes();
    
    // Valor total de vendas por período
    @Query("SELECT COALESCE(SUM(p.valorFinal), 0) FROM Pedido p WHERE p.dataPedido BETWEEN :dataInicio AND :dataFim AND p.status != 'CANCELADO'")
    BigDecimal calcularVendasPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                                        @Param("dataFim") LocalDateTime dataFim);
    
    // Ticket médio do mês
    @Query("SELECT AVG(p.valorFinal) FROM Pedido p WHERE FUNCTION('MONTH', p.dataPedido) = FUNCTION('MONTH', CURRENT_TIMESTAMP) AND FUNCTION('YEAR', p.dataPedido) = FUNCTION('YEAR', CURRENT_TIMESTAMP) AND p.status != 'CANCELADO'")
    BigDecimal calcularTicketMedioDoMes();
    
    // Pedidos mais recentes
    List<Pedido> findTop10ByOrderByDataPedidoDesc();
    
    // Buscar pedidos com entrega prevista para hoje
    @Query("SELECT p FROM Pedido p WHERE FUNCTION('DATE', p.dataEntregaPrevista) = FUNCTION('CURRENT_DATE') AND p.status NOT IN ('ENTREGUE', 'CANCELADO') ORDER BY p.dataEntregaPrevista ASC")
    List<Pedido> findPedidosComEntregaHoje();
    
    // Buscar pedidos atrasados
    @Query("SELECT p FROM Pedido p WHERE p.dataEntregaPrevista < CURRENT_TIMESTAMP AND p.status NOT IN ('ENTREGUE', 'CANCELADO') ORDER BY p.dataEntregaPrevista ASC")
    List<Pedido> findPedidosAtrasados();
    
    // Buscar todos os pedidos ordenados por data
    List<Pedido> findAllByOrderByDataPedidoDesc();
    
    Page<Pedido> findAllByOrderByDataPedidoDesc(Pageable pageable);
    
    // Buscar pedidos por status ordenados por data
    List<Pedido> findByStatusOrderByDataPedidoDesc(StatusPedido status);
    
    // Estatísticas: total de pedidos por tipo no período
    @Query("SELECT p.tipoPedido, COUNT(p) FROM Pedido p WHERE p.dataPedido BETWEEN :dataInicio AND :dataFim GROUP BY p.tipoPedido")
    List<Object[]> contarPedidosPorTipoPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                                               @Param("dataFim") LocalDateTime dataFim);
    
    // Estatísticas: total de pedidos por status
    @Query("SELECT p.status, COUNT(p) FROM Pedido p GROUP BY p.status")
    List<Object[]> contarPedidosPorStatus();
}
