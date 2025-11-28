package app.projeto.service;

import app.projeto.entity.Pedido;
import app.projeto.entity.Pedido.StatusPedido;
import app.projeto.entity.Pedido.TipoPedido;
import app.projeto.entity.ItemPedido;
import app.projeto.entity.Cliente;
import app.projeto.entity.Produto;
import app.projeto.repository.PedidoRepository;
import app.projeto.repository.ItemPedidoRepository;
import app.projeto.repository.ClienteRepository;
import app.projeto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAllByOrderByDataPedidoDesc();
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorTipo(TipoPedido tipo) {
        return pedidoRepository.findByTipoPedido(tipo);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPendentes() {
        return pedidoRepository.findPedidosPendentes();
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarEmPreparo() {
        return pedidoRepository.findPedidosEmPreparo();
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarProntos() {
        return pedidoRepository.findPedidosProntos();
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarDoDia() {
        return pedidoRepository.findPedidosDoDia();
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarDaSemana() {
        return pedidoRepository.findPedidosDaSemana();
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarDoMes() {
        return pedidoRepository.findPedidosDoMes();
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return pedidoRepository.findByPeriodo(inicio, fim);
    }

    @Transactional(readOnly = true)
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Transactional
    public Pedido criar(Pedido pedido) {
        // Valida e carrega o cliente
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new RuntimeException("Cliente é obrigatório");
        }
        
        Cliente cliente = clienteRepository.findById(pedido.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        
        // Salva o pedido primeiro
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        
        // Processa os itens
        if (pedido.getItens() != null && !pedido.getItens().isEmpty()) {
            for (ItemPedido item : pedido.getItens()) {
                item.setPedido(pedidoSalvo);
                
                // Carrega o produto
                Produto produto = produtoRepository.findById(item.getProduto().getId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + item.getProduto().getId()));
                
                item.setProduto(produto);
                item.setPrecoUnitario(produto.getPreco());
                
                // Diminui o estoque
                produto.diminuirEstoque(item.getQuantidade());
                produtoRepository.save(produto);
            }
            itemPedidoRepository.saveAll(pedido.getItens());
        }
        
        return pedidoRepository.findById(pedidoSalvo.getId()).orElseThrow();
    }

    @Transactional
    public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (!pedidoExistente.podeSerEditado()) {
            throw new RuntimeException("Pedido não pode ser editado no status atual");
        }

        pedidoExistente.setObservacoes(pedidoAtualizado.getObservacoes());
        pedidoExistente.setFormaPagamento(pedidoAtualizado.getFormaPagamento());
        pedidoExistente.setEnderecoEntrega(pedidoAtualizado.getEnderecoEntrega());
        pedidoExistente.setTelefoneContato(pedidoAtualizado.getTelefoneContato());
        pedidoExistente.setDataEntregaPrevista(pedidoAtualizado.getDataEntregaPrevista());
        pedidoExistente.setValorDesconto(pedidoAtualizado.getValorDesconto());

        return pedidoRepository.save(pedidoExistente);
    }

    @Transactional
    public void confirmar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.confirmar();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        if (!pedido.podeSerCancelado()) {
            throw new RuntimeException("Pedido não pode ser cancelado no status atual");
        }
        
        // Devolve os produtos ao estoque
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            produto.aumentarEstoque(item.getQuantidade());
            produtoRepository.save(produto);
        }
        
        pedido.cancelar();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(novoStatus);
        
        if (novoStatus == StatusPedido.ENTREGUE) {
            pedido.setDataEntregaRealizada(LocalDateTime.now());
        }
        
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void marcarComoEntregue(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.marcarComoEntregue();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public ItemPedido adicionarItem(Long pedidoId, Long produtoId, Integer quantidade, String observacoes) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        if (!pedido.podeSerEditado()) {
            throw new RuntimeException("Pedido não pode ser editado no status atual");
        }
        
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        
        ItemPedido item = new ItemPedido(pedido, produto, quantidade, observacoes);
        itemPedidoRepository.save(item);
        
        // Diminui o estoque
        produto.diminuirEstoque(quantidade);
        produtoRepository.save(produto);
        
        return item;
    }

    @Transactional
    public void removerItem(Long pedidoId, Long itemId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        if (!pedido.podeSerEditado()) {
            throw new RuntimeException("Pedido não pode ser editado no status atual");
        }
        
        ItemPedido item = itemPedidoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        
        // Devolve ao estoque
        Produto produto = item.getProduto();
        produto.aumentarEstoque(item.getQuantidade());
        produtoRepository.save(produto);
        
        itemPedidoRepository.delete(item);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularVendasDoDia() {
        return pedidoRepository.calcularVendasDoDia();
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularVendasDoMes() {
        return pedidoRepository.calcularVendasDoMes();
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularVendasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return pedidoRepository.calcularVendasPorPeriodo(inicio, fim);
    }

    @Transactional(readOnly = true)
    public BigDecimal calcularTicketMedioDoMes() {
        return pedidoRepository.calcularTicketMedioDoMes();
    }

    @Transactional(readOnly = true)
    public Long contarPorStatus(StatusPedido status) {
        return pedidoRepository.countByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<ItemPedido> listarItensPedido(Long pedidoId) {
        return itemPedidoRepository.findByPedidoId(pedidoId);
    }
}
