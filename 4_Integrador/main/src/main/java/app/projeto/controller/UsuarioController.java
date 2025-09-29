package app.projeto.controller;

import app.projeto.entity.Usuario;
import app.projeto.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @GetMapping("/buscar")
    public List<Usuario> buscarPorNome(@RequestParam String nome) {
        return usuarioService.buscarPorNome(nome);
    }

    @GetMapping("/cidade")
    public List<Usuario> buscarPorCidade(@RequestParam String cidade) {
        return usuarioService.buscarPorCidade(cidade);
    }

    @GetMapping("/busca-por")
    public List<Usuario> buscarPorNomeECidade(@RequestParam String nome, @RequestParam String cidade) {
        return usuarioService.buscarPorNomeECidade(nome, cidade);
    }

    @PostMapping
    public Usuario criar(@Valid @RequestBody Usuario usuario) {
        return usuarioService.criar(usuario);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return usuarioService.atualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }
}
