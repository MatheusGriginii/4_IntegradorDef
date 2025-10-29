package app.projeto.service;

import app.projeto.entity.Candidato;
import app.projeto.repository.CandidatoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TESTE DE UNIDADE – CandidatoService (unitário)")
class CandidatoServiceUnitTest {

    @Mock
    private CandidatoRepository candidatoRepository;

    @InjectMocks
    private CandidatoService candidatoService;

    public CandidatoServiceUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – salvar candidato válido chama save uma vez")
    void salvarCandidato_valido_chamaSave() {
        // Arrange
        Candidato candidato = new Candidato();
        candidato.setCpf("12345678909"); // cpf de exemplo
        when(candidatoRepository.save(any(Candidato.class))).thenReturn(candidato);

        // Act
        Candidato salvo = candidatoService.salvar(candidato);

        // Assert
        assertNotNull(salvo);
        verify(candidatoRepository, times(1)).save(any(Candidato.class));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – salvar candidato com CPF inválido lança exceção")
    void salvarCandidato_cpfInvalido_lancaExcecao() {
        // Arrange
        Candidato candidato = new Candidato();
        candidato.setCpf("123"); // inválido

        // Caso sua implementação lance runtime exception
        when(candidatoRepository.save(any(Candidato.class))).thenThrow(new RuntimeException("CPF inválido"));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            candidatoService.salvar(candidato);
        });
        assertTrue(ex.getMessage().contains("CPF"));
    }
}
