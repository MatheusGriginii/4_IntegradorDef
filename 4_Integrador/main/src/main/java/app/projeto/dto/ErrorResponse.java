package app.projeto.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String mensagem;
    private LocalDateTime timestamp;
    private int status;

    public ErrorResponse(String mensagem, int status) {
        this.mensagem = mensagem;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }

    // Getters e Setters
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
