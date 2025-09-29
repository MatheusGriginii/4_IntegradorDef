package app.projeto.controller;

import app.projeto.entity.Vaga;
import app.projeto.service.VagaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vagas")
public class VagaController {
    private final VagaService vagaService;

    public VagaController(VagaService vagaService) {
        this.vagaService = vagaService;
    }

    @GetMapping
    public List<Vaga> listar() {
        return vagaService.listar();
    }

    @GetMapping("/{id}")
    public Vaga buscarPorId(@PathVariable Long id) {
        return vagaService.buscarPorId(id);
    }

    @GetMapping("/buscar")
    public List<Vaga> buscarPorTitulo(@RequestParam String titulo) {
        return vagaService.buscarPorTitulo(titulo);
    }

    @GetMapping("/por-empresa")
    public List<Vaga> buscarPorEmpresa(@RequestParam String empresaNome) {
        return vagaService.buscarPorEmpresa(empresaNome);
    }

    @PostMapping
    public Vaga criar(@Valid @RequestBody Vaga vaga) {
        return vagaService.criar(vaga);
    }

    @PutMapping("/{id}")
    public Vaga atualizar(@PathVariable Long id, @Valid @RequestBody Vaga vaga) {
        return vagaService.atualizar(id, vaga);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        vagaService.deletar(id);
    }
}
