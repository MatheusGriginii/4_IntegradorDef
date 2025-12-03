package app.projeto.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // CLIENTE DESABILITADO - Sistema agora permite pedidos sem cliente
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "cliente_id", nullable = true)
    // private Cliente cliente;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<ItemPedido> itens = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPedido status = StatusPedido.PENDENTE;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pedido", nullable = false, length = 20)
    private TipoPedido tipoPedido = TipoPedido.BALCAO;
    
    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;
    
    @Column(name = "valor_desconto", precision = 10, scale = 2)
    private BigDecimal valorDesconto = BigDecimal.ZERO;
    
    @Column(name = "valor_final", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorFinal = BigDecimal.ZERO;
    
    @Column(name = "observacoes", length = 500)
    private String observacoes;
    
    @Column(name = "data_pedido", nullable = false, updatable = false)
    private LocalDateTime dataPedido;
    
    @Column(name = "data_entrega_prevista")
    private LocalDateTime dataEntregaPrevista;
    
    @Column(name = "data_entrega_realizada")
    private LocalDateTime dataEntregaRealizada;
    
    @Column(name = "forma_pagamento", length = 20)
    private String formaPagamento;
    
    @Column(name = "endereco_entrega", length = 500)
    private String enderecoEntrega;
    
    @Column(name = "telefone_contato", length = 15)
    private String telefoneContato;
    
    // Enums
    public enum StatusPedido {
        PENDENTE("Pendente"),
        CONFIRMADO("Confirmado"),
        PREPARANDO("Preparando"),
        PRONTO("Pronto"),
        ENTREGUE("Entregue"),
        CANCELADO("Cancelado");
        
        private final String descricao;
        
        StatusPedido(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    public enum TipoPedido {
        BALCAO("Balcão"),
        DELIVERY("Delivery"),
        RETIRADA("Retirada");
        
        private final String descricao;
        
        TipoPedido(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    // Construtores
    public Pedido() {
        this.dataPedido = LocalDateTime.now();
    }
    
    // CONSTRUTORES COM CLIENTE DESABILITADOS
    // public Pedido(Cliente cliente) {
    //     this();
    //     this.cliente = cliente;
    // }
    
    // public Pedido(Cliente cliente, TipoPedido tipoPedido) {
    //     this(cliente);
    //     this.tipoPedido = tipoPedido;
    // }
    
    // Métodos de callback JPA
    @PrePersist
    @PreUpdate
    protected void calcularValores() {
        this.valorTotal = itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        this.valorFinal = this.valorTotal.subtract(this.valorDesconto);
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    // CLIENTE DESABILITADO
    // public Cliente getCliente() {
    //     return cliente;
    // }
    
    // public void setCliente(Cliente cliente) {
    //     this.cliente = cliente;
    // }
    
    public List<ItemPedido> getItens() {
        return itens;
    }
    
    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
    
    public StatusPedido getStatus() {
        return status;
    }
    
    public void setStatus(StatusPedido status) {
        this.status = status;
    }
    
    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }
    
    public void setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }
    
    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }
    
    public BigDecimal getValorFinal() {
        return valorFinal;
    }
    
    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public LocalDateTime getDataPedido() {
        return dataPedido;
    }
    
    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }
    
    public LocalDateTime getDataEntregaPrevista() {
        return dataEntregaPrevista;
    }
    
    public void setDataEntregaPrevista(LocalDateTime dataEntregaPrevista) {
        this.dataEntregaPrevista = dataEntregaPrevista;
    }
    
    public LocalDateTime getDataEntregaRealizada() {
        return dataEntregaRealizada;
    }
    
    public void setDataEntregaRealizada(LocalDateTime dataEntregaRealizada) {
        this.dataEntregaRealizada = dataEntregaRealizada;
    }
    
    public String getFormaPagamento() {
        return formaPagamento;
    }
    
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }
    
    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }
    
    public String getTelefoneContato() {
        return telefoneContato;
    }
    
    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }
    
    // Métodos utilitários
    public void adicionarItem(Produto produto, int quantidade) {
        ItemPedido item = new ItemPedido(this, produto, quantidade);
        itens.add(item);
        calcularValores();
    }
    
    public void removerItem(ItemPedido item) {
        itens.remove(item);
        item.setPedido(null);
        calcularValores();
    }
    
    public void limparItens() {
        itens.clear();
        calcularValores();
    }
    
    public int getTotalItens() {
        return itens.stream().mapToInt(ItemPedido::getQuantidade).sum();
    }
    
    public boolean podeSerCancelado() {
        return status == StatusPedido.PENDENTE || status == StatusPedido.CONFIRMADO;
    }
    
    public boolean podeSerEditado() {
        return status == StatusPedido.PENDENTE;
    }
    
    public void confirmar() {
        if (status == StatusPedido.PENDENTE) {
            this.status = StatusPedido.CONFIRMADO;
        } else {
            throw new IllegalStateException("Pedido não pode ser confirmado no status atual: " + status);
        }
    }
    
    public void cancelar() {
        if (podeSerCancelado()) {
            this.status = StatusPedido.CANCELADO;
        } else {
            throw new IllegalStateException("Pedido não pode ser cancelado no status atual: " + status);
        }
    }
    
    public void marcarComoEntregue() {
        this.status = StatusPedido.ENTREGUE;
        this.dataEntregaRealizada = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                // ", cliente=" + (cliente != null ? cliente.getNome() : "null") +
                ", status=" + status +
                ", tipoPedido=" + tipoPedido +
                ", valorTotal=" + valorTotal +
                ", valorFinal=" + valorFinal +
                ", totalItens=" + itens.size() +
                ", dataPedido=" + dataPedido +
                '}';
    }
}