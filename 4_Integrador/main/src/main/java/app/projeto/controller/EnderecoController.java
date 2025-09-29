package app.projeto.controller;

import app.projeto.entity.Endereco;
import app.projeto.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin(origins = "*")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<Endereco>> listarTodos() {
        List<Endereco> enderecos = enderecoService.listarTodos();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable Long id) {
        Optional<Endereco> endereco = enderecoService.buscarPorId(id);
        return endereco.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Endereco> criar(@RequestBody Endereco endereco) {
        try {
            Endereco novoEndereco = enderecoService.salvar(endereco);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEndereco);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable Long id, @RequestBody Endereco endereco) {
        try {
            endereco.setId(id);
            Endereco enderecoAtualizado = enderecoService.salvar(endereco);
            return ResponseEntity.ok(enderecoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            enderecoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/cidade")
    public ResponseEntity<List<Endereco>> buscarPorCidade(@RequestParam String cidade) {
        List<Endereco> enderecos = enderecoService.buscarPorCidade(cidade);
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/estado")
    public ResponseEntity<List<Endereco>> buscarPorEstado(@RequestParam String estado) {
        List<Endereco> enderecos = enderecoService.buscarPorEstado(estado);
        return ResponseEntity.ok(enderecos);
    }
}
