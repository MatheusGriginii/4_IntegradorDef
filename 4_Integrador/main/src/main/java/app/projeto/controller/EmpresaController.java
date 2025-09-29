package app.projeto.controller;

import app.projeto.entity.Empresa;
import app.projeto.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public List<Empresa> listar() {
        return empresaService.listar();
    }

    @GetMapping("/{id}")
    public Empresa buscarPorId(@PathVariable Long id) {
        return empresaService.buscarPorId(id);
    }

    @GetMapping("/buscar")
    public List<Empresa> buscarPorNome(@RequestParam String nome) {
        return empresaService.buscarPorNome(nome);
    }

    @GetMapping("/por-titulo")
    public List<Empresa> buscarPorTituloVaga(@RequestParam String titulo) {
        return empresaService.buscarPorTituloVaga(titulo);
    }

    @PostMapping
    public Empresa criar(@Valid @RequestBody Empresa empresa) {
        return empresaService.criar(empresa);
    }

    @PutMapping("/{id}")
    public Empresa atualizar(@PathVariable Long id, @Valid @RequestBody Empresa empresa) {
        return empresaService.atualizar(id, empresa);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        empresaService.deletar(id);
    }
}
