package app.projeto.dto;

import app.projeto.entity.Usuario.PerfilUsuario;

public class LoginResponse {
    
    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String nome;
    private String email;
    private PerfilUsuario perfil;

    // Construtores
    public LoginResponse() {}

    public LoginResponse(String token, Long id, String nome, String email, PerfilUsuario perfil) {
        this.token = token;
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }
}
