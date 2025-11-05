package app.projeto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome da categoria é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    @Column(nullable = false, length = 50, unique = true)
    private String nome;
    
    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    @Column(length = 255)
    private String descricao;
    
    @Column(name = "categoria_ativa", nullable = false)
    private Boolean ativa = true;
    
    @Column(length = 255)
    private String icone;
    
    @Column(length = 7)
    private String cor = "#C8102E"; // Cor padrão da ADORELA
    
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produto> produtos = new ArrayList<>();
    
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    // Construtores
    public Categoria() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    public Categoria(String nome, String descricao) {
        this();
        this.nome = nome;
        this.descricao = descricao;
    }
    
    public Categoria(String nome, String descricao, String icone) {
        this(nome, descricao);
        this.icone = icone;
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
    
    public Boolean getAtiva() {
        return ativa;
    }
    
    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }
    
    public String getIcone() {
        return icone;
    }
    
    public void setIcone(String icone) {
        this.icone = icone;
    }
    
    public String getCor() {
        return cor;
    }
    
    public void setCor(String cor) {
        this.cor = cor;
    }
    
    public List<Produto> getProdutos() {
        return produtos;
    }
    
    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
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
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
        produto.setCategoria(this);
    }
    
    public void removerProduto(Produto produto) {
        produtos.remove(produto);
        produto.setCategoria(null);
    }
    
    public int getTotalProdutos() {
        return produtos.size();
    }
    
    public int getTotalProdutosAtivos() {
        return (int) produtos.stream().filter(Produto::getAtivo).count();
    }
    
    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ativa=" + ativa +
                ", totalProdutos=" + produtos.size() +
                '}';
    }
}