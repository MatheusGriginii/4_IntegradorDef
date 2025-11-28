package app.projeto.controller;

import app.projeto.entity.Pedido;
import app.projeto.entity.Pedido.StatusPedido;
import app.projeto.entity.Pedido.TipoPedido;
import app.projeto.entity.ItemPedido;
import app.projeto.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.listarPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pedido>> listarPorStatus(@PathVariable StatusPedido status) {
        List<Pedido> pedidos = pedidoService.listarPorStatus(status);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Pedido>> listarPorTipo(@PathVariable TipoPedido tipo) {
        List<Pedido> pedidos = pedidoService.listarPorTipo(tipo);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<Pedido>> listarPendentes() {
        List<Pedido> pedidos = pedidoService.listarPendentes();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/em-preparo")
    public ResponseEntity<List<Pedido>> listarEmPreparo() {
        List<Pedido> pedidos = pedidoService.listarEmPreparo();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/prontos")
    public ResponseEntity<List<Pedido>> listarProntos() {
        List<Pedido> pedidos = pedidoService.listarProntos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/do-dia")
    public ResponseEntity<List<Pedido>> listarDoDia() {
        List<Pedido> pedidos = pedidoService.listarDoDia();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/da-semana")
    public ResponseEntity<List<Pedido>> listarDaSemana() {
        List<Pedido> pedidos = pedidoService.listarDaSemana();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/do-mes")
    public ResponseEntity<List<Pedido>> listarDoMes() {
        List<Pedido> pedidos = pedidoService.listarDoMes();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Pedido>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<Pedido> pedidos = pedidoService.listarPorPeriodo(inicio, fim);
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@Valid @RequestBody Pedido pedido) {
        try {
            Pedido pedidoCriado = pedidoService.criar(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @Valid @RequestBody Pedido pedido) {
        try {
            Pedido pedidoAtualizado = pedidoService.atualizar(id, pedido);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmar(@PathVariable Long id) {
        try {
            pedidoService.confirmar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        try {
            pedidoService.cancelar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestParam StatusPedido status) {
        try {
            pedidoService.atualizarStatus(id, status);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/entregar")
    public ResponseEntity<Void> marcarComoEntregue(@PathVariable Long id) {
        try {
            pedidoService.marcarComoEntregue(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{pedidoId}/itens")
    public ResponseEntity<ItemPedido> adicionarItem(
            @PathVariable Long pedidoId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade,
            @RequestParam(required = false) String observacoes) {
        try {
            ItemPedido item = pedidoService.adicionarItem(pedidoId, produtoId, quantidade, observacoes);
            return ResponseEntity.status(HttpStatus.CREATED).body(item);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{pedidoId}/itens/{itemId}")
    public ResponseEntity<Void> removerItem(@PathVariable Long pedidoId, @PathVariable Long itemId) {
        try {
            pedidoService.removerItem(pedidoId, itemId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{pedidoId}/itens")
    public ResponseEntity<List<ItemPedido>> listarItens(@PathVariable Long pedidoId) {
        List<ItemPedido> itens = pedidoService.listarItensPedido(pedidoId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/estatisticas/vendas-dia")
    public ResponseEntity<BigDecimal> calcularVendasDoDia() {
        BigDecimal vendas = pedidoService.calcularVendasDoDia();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/estatisticas/vendas-mes")
    public ResponseEntity<BigDecimal> calcularVendasDoMes() {
        BigDecimal vendas = pedidoService.calcularVendasDoMes();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/estatisticas/vendas-periodo")
    public ResponseEntity<BigDecimal> calcularVendasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        BigDecimal vendas = pedidoService.calcularVendasPorPeriodo(inicio, fim);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/estatisticas/ticket-medio")
    public ResponseEntity<BigDecimal> calcularTicketMedioDoMes() {
        BigDecimal ticketMedio = pedidoService.calcularTicketMedioDoMes();
        return ResponseEntity.ok(ticketMedio);
    }

    @GetMapping("/estatisticas/contar/{status}")
    public ResponseEntity<Long> contarPorStatus(@PathVariable StatusPedido status) {
        Long total = pedidoService.contarPorStatus(status);
        return ResponseEntity.ok(total);
    }
}
