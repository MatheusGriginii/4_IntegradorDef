package app.projeto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    @JsonIgnoreProperties("usuarios")
    @NotNull(message = "O campo endereco é obrigatório")
    private Endereco endereco;

    @ManyToMany
    @JoinTable(
            name = "usuario_vaga",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "vaga_id")
    )
    @JsonIgnoreProperties("usuarios")
    @NotEmpty(message = "O usuário deve estar associado a pelo menos uma vaga")
    private List<Vaga> vagas;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    public List<Vaga> getVagas() { return vagas; }
    public void setVagas(List<Vaga> vagas) { this.vagas = vagas; }
}
