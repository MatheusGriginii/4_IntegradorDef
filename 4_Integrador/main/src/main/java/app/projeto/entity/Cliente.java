package app.projeto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Size(max = 100, message = "Sobrenome deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String sobrenome;
    
    @Email(message = "Email deve ter formato válido")
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
    @Column(length = 150, unique = true)
    private String email;
    
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
    @Column(length = 11)
    private String telefone;
    
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
    @Column(length = 11, unique = true)
    private String cpf;
    
    @Past(message = "Data de nascimento deve ser no passado")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Genero genero;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Pedido> pedidos = new ArrayList<>();
    
    @Column(name = "cliente_ativo", nullable = false)
    private Boolean ativo = true;
    
    @Column(name = "preferencias", length = 500)
    private String preferencias;
    
    @Column(name = "observacoes", length = 1000)
    private String observacoes;
    
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    // Enum para Gênero
    public enum Genero {
        MASCULINO, FEMININO, OUTRO, NAO_INFORMADO
    }
    
    // Construtores
    public Cliente() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    public Cliente(String nome, String email) {
        this();
        this.nome = nome;
        this.email = email;
    }
    
    public Cliente(String nome, String sobrenome, String email, String telefone) {
        this(nome, email);
        this.sobrenome = sobrenome;
        this.telefone = telefone;
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
    
    public String getSobrenome() {
        return sobrenome;
    }
    
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public Genero getGenero() {
        return genero;
    }
    
    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    
    public Endereco getEndereco() {
        return endereco;
    }
    
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public List<Pedido> getPedidos() {
        return pedidos;
    }
    
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    
    public Boolean getAtivo() {
        return ativo;
    }
    
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    
    public String getPreferencias() {
        return preferencias;
    }
    
    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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
    public String getNomeCompleto() {
        return sobrenome != null ? nome + " " + sobrenome : nome;
    }
    
    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
        pedido.setCliente(this);
    }
    
    public void removerPedido(Pedido pedido) {
        pedidos.remove(pedido);
        pedido.setCliente(null);
    }
    
    public int getTotalPedidos() {
        return pedidos.size();
    }
    
    public int getIdade() {
        return dataNascimento != null ? 
            LocalDate.now().getYear() - dataNascimento.getYear() : 0;
    }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + getNomeCompleto() + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", ativo=" + ativo +
                ", totalPedidos=" + pedidos.size() +
                '}';
    }
}