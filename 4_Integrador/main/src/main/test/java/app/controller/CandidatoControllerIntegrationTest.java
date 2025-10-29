package app.projeto.controller;

import app.projeto.entity.Candidato;
import app.projeto.service.CandidatoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(CandidatoController.class)
@DisplayName("TESTE DE INTEGRAÇÃO – CandidatoController (com service mockado)")
class CandidatoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidatoService candidatoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Deve retornar 400 ao cadastrar candidato com CPF inválido")
    void cadastrarCpfInvalido_retorna400() throws Exception {
        Candidato candidato = new Candidato();
        candidato.setCpf("123");

        Mockito.when(candidatoService.salvar(any())).thenThrow(new RuntimeException("CPF inválido"));

        mockMvc.perform(post("/candidatos")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(candidato)))
                .andExpect(status().isBadRequest());
    }
}
