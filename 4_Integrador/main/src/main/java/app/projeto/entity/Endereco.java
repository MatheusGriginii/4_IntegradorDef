package app.projeto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enderecos")
public class Endereco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve ter 8 dígitos")
    @Column(length = 8, nullable = false)
    private String cep;

    @NotBlank(message = "Logradouro é obrigatório")
    @Size(min = 5, max = 200, message = "Logradouro deve ter entre 5 e 200 caracteres")
    @Column(nullable = false, length = 200)
    private String logradouro;

    @NotBlank(message = "Número é obrigatório")
    @Size(max = 10, message = "Número deve ter no máximo 10 caracteres")
    @Column(nullable = false, length = 10)
    private String numero;

    @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String complemento;

    @NotBlank(message = "Bairro é obrigatório")
    @Size(min = 2, max = 100, message = "Bairro deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String bairro;

    @NotBlank(message = "Cidade é obrigatória")
    @Size(min = 2, max = 100, message = "Cidade deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
    @Column(nullable = false, length = 2)
    private String estado;

    @Column(length = 200)
    private String pontoReferencia;

    @Column(name = "endereco_ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "area_entrega", nullable = false)
    private Boolean areaEntrega = true;

    @Column(name = "taxa_entrega", precision = 10, scale = 2)
    private java.math.BigDecimal taxaEntrega;

    @Column(name = "observacoes", length = 255)
    private String observacoes;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Construtores
    public Endereco() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public Endereco(String cep, String logradouro, String numero, String bairro, String cidade, String estado) {
        this();
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getAreaEntrega() {
        return areaEntrega;
    }

    public void setAreaEntrega(Boolean areaEntrega) {
        this.areaEntrega = areaEntrega;
    }

    public java.math.BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(java.math.BigDecimal taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
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
    public String getEnderecoCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append(logradouro).append(", ").append(numero);
        
        if (complemento != null && !complemento.trim().isEmpty()) {
            sb.append(" - ").append(complemento);
        }
        
        sb.append(", ").append(bairro)
          .append(", ").append(cidade)
          .append(" - ").append(estado)
          .append(", CEP: ").append(formatarCep());
        
        return sb.toString();
    }

    public String getEnderecoResumido() {
        return logradouro + ", " + numero + " - " + bairro + ", " + cidade;
    }

    public String formatarCep() {
        if (cep != null && cep.length() == 8) {
            return cep.substring(0, 5) + "-" + cep.substring(5);
        }
        return cep;
    }

    public boolean temComplemento() {
        return complemento != null && !complemento.trim().isEmpty();
    }

    public boolean temPontoReferencia() {
        return pontoReferencia != null && !pontoReferencia.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", cep='" + formatarCep() + '\'' +
                ", areaEntrega=" + areaEntrega +
                ", ativo=" + ativo +
                '}';
    }
}
