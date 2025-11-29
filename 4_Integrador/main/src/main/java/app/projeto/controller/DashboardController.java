package app.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.projeto.repository.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * Retorna estatísticas para o dashboard
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Contar produtos ativos
        long totalProdutos = produtoRepository.countByAtivoTrue();
        
        // Contar todas as categorias ativas
        long totalCategorias = categoriaRepository.countByAtivaTrue();
        
        // Contar todos os clientes ativos
        long totalClientes = clienteRepository.countByAtivoTrue();
        
        // Contar pedidos de hoje
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicioDia = hoje.atStartOfDay();
        LocalDateTime fimDia = hoje.plusDays(1).atStartOfDay();
        long totalPedidosHoje = pedidoRepository.countByDataPedidoBetween(inicioDia, fimDia);
        
        stats.put("totalProdutos", totalProdutos);
        stats.put("totalCategorias", totalCategorias);
        stats.put("totalClientes", totalClientes);
        stats.put("totalPedidosHoje", totalPedidosHoje);
        
        return ResponseEntity.ok(stats);
    }
}
