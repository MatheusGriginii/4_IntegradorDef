package app.projeto.controller;

import app.projeto.entity.Cliente;
import app.projeto.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Cliente>> listarAtivos() {
        List<Cliente> clientesAtivos = clienteService.listarAtivos();
        return ResponseEntity.ok(clientesAtivos);
    }

    @GetMapping("/pagina")
    public ResponseEntity<Page<Cliente>> listarPaginado(Pageable pageable) {
        Page<Cliente> clientes = clienteService.listarPaginado(pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        return cliente.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        List<Cliente> clientes = clienteService.buscarPorNome(nome);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> buscarPorEmail(@PathVariable String email) {
        Optional<Cliente> cliente = clienteService.buscarPorEmail(email);
        return cliente.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        Optional<Cliente> cliente = clienteService.buscarPorCpf(cpf);
        return cliente.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cidade")
    public ResponseEntity<List<Cliente>> buscarPorCidade(@RequestParam String cidade) {
        List<Cliente> clientes = clienteService.buscarPorCidade(cidade);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/aniversario-hoje")
    public ResponseEntity<List<Cliente>> clientesAniversarioHoje() {
        List<Cliente> clientes = clienteService.clientesAniversarioHoje();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<Cliente>> clientesRecentes() {
        List<Cliente> clientes = clienteService.clientesRecentes();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente) {
        try {
            Cliente clienteSalvo = clienteService.salvar(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        try {
            cliente.setId(id);
            Cliente clienteAtualizado = clienteService.salvar(cliente);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Cliente> alterarStatus(@PathVariable Long id, @RequestBody boolean ativo) {
        try {
            Cliente cliente = clienteService.alterarStatus(id, ativo);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            clienteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/permanente")
    public ResponseEntity<Void> deletarPermanentemente(@PathVariable Long id) {
        try {
            clienteService.deletarPermanentemente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<?> obterEstatisticas() {
        try {
            var estatisticas = clienteService.obterEstatisticas();
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}