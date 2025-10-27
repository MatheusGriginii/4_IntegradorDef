package app.projeto.controller;

import app.projeto.entity.Candidato;
import app.projeto.service.CandidatoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CandidatoController.class)
@DisplayName("TESTES DE INTEGRAÇÃO – CandidatoController")
public class CandidatoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidatoService candidatoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Candidato candidato;

    @BeforeEach
    void setup() {
        candidato = new Candidato();
        candidato.setNome("João Teste");
        candidato.setCpf("12345678909");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve retornar 201 ao cadastrar candidato válido")
    void deveCadastrarCandidatoValido() throws Exception {
        Mockito.when(candidatoService.salvar(any(Candidato.class))).thenReturn(candidato);

        mockMvc.perform(post("/candidatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(candidato)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve retornar 400 ao tentar cadastrar candidato com CPF inválido")
    void deveRetornar400CpfInvalido() throws Exception {
        candidato.setCpf("123"); // CPF inválido

        Mockito.when(candidatoService.salvar(any(Candidato.class)))
                .thenThrow(new RuntimeException("CPF inválido"));

        mockMvc.perform(post("/candidatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(candidato)))
                .andExpect(status().isBadRequest());
    }
}
