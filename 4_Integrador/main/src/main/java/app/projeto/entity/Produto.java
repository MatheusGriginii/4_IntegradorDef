package app.projeto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    @NotNull(message = "Categoria é obrigatória")
    @JsonIgnoreProperties({"produtos", "hibernateLazyInitializer", "handler"})
    private Categoria categoria;
    
    @Min(value = 0, message = "Estoque não pode ser negativo")
    @Column(nullable = false)
    private Integer estoque = 0;
    
    @Column(name = "estoque_minimo")
    private Integer estoqueMinimo = 0;
    
    @Column(name = "produto_ativo", nullable = false)
    private Boolean ativo = true;
    
    @Column(length = 255)
    private String imagem;
    
    @Column(name = "tempo_preparo_minutos")
    private Integer tempoPreparoMinutos;
    
    @Column(name = "ingredientes", length = 1000)
    private String ingredientes;
    
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    // Construtores
    public Produto() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    public Produto(String nome, String descricao, BigDecimal preco, Categoria categoria) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }
    
    // Métodos de callback JPA
    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public BigDecimal getPreco() {
        return preco;
    }
    
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    
    public Categoria getCategoria() {
        return categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public Integer getEstoque() {
        return estoque;
    }
    
    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }
    
    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }
    
    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }
    
    public Boolean getAtivo() {
        return ativo;
    }
    
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    
    public String getImagem() {
        return imagem;
    }
    
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    
    public Integer getTempoPreparoMinutos() {
        return tempoPreparoMinutos;
    }
    
    public void setTempoPreparoMinutos(Integer tempoPreparoMinutos) {
        this.tempoPreparoMinutos = tempoPreparoMinutos;
    }
    
    public String getIngredientes() {
        return ingredientes;
    }
    
    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    
    // Métodos utilitários
    public boolean isEstoqueBaixo() {
        return estoque <= estoqueMinimo;
    }
    
    public void diminuirEstoque(int quantidade) {
        if (quantidade > 0 && estoque >= quantidade) {
            this.estoque -= quantidade;
            this.dataAtualizacao = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Quantidade inválida ou estoque insuficiente");
        }
    }
    
    public void aumentarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.estoque += quantidade;
            this.dataAtualizacao = LocalDateTime.now();
        } else {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
    }
    
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", categoria=" + (categoria != null ? categoria.getNome() : "null") +
                ", estoque=" + estoque +
                ", ativo=" + ativo +
                '}';
    }
}