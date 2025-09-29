package app.projeto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo título da vaga é obrigatório")
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @JsonIgnoreProperties("vagas")
    private Empresa empresa;

    @ManyToMany
    @JoinTable(
            name = "vaga_candidato",
            joinColumns = @JoinColumn(name = "vaga_id"),
            inverseJoinColumns = @JoinColumn(name = "candidato_id")
    )
    @JsonIgnoreProperties("vagas")
    private List<Candidato> candidatos;

    @ManyToMany(mappedBy = "vagas")
    @JsonIgnoreProperties("vagas")
    private List<Usuario> usuarios;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    public List<Candidato> getCandidatos() { return candidatos; }
    public void setCandidatos(List<Candidato> candidatos) { this.candidatos = candidatos; }

    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
}
