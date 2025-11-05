package app.projeto.service;

import app.projeto.entity.Cliente;
import app.projeto.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAllWithEndereco();
    }

    public List<Cliente> listarAtivos() {
        return clienteRepository.findByAtivoTrue();
    }

    public Page<Cliente> listarPaginado(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmailIgnoreCase(email);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public List<Cliente> buscarPorCidade(String cidade) {
        return clienteRepository.findByEndereco_CidadeIgnoreCase(cidade);
    }

    public List<Cliente> clientesAniversarioHoje() {
        LocalDate hoje = LocalDate.now();
        return clienteRepository.findByDataNascimentoDayAndMonth(hoje.getDayOfMonth(), hoje.getMonthValue());
    }

    public List<Cliente> clientesRecentes() {
        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        return clienteRepository.findByDataCriacaoAfterOrderByDataCriacaoDesc(inicioMes);
    }

    public Cliente salvar(Cliente cliente) {
        // Se for novo cliente, definir como ativo
        if (cliente.getId() == null) {
            cliente.setAtivo(true);
            cliente.setDataCriacao(LocalDateTime.now());
        } else {
            cliente.setDataAtualizacao(LocalDateTime.now());
        }
        
        return clienteRepository.save(cliente);
    }

    public Cliente alterarStatus(Long id, boolean ativo) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        cliente.setAtivo(ativo);
        cliente.setDataAtualizacao(LocalDateTime.now());
        
        return clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        // Soft delete - apenas marca como inativo
        cliente.setAtivo(false);
        cliente.setDataAtualizacao(LocalDateTime.now());
        clienteRepository.save(cliente);
    }

    public void deletarPermanentemente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado");
        }
        clienteRepository.deleteById(id);
    }

    public Map<String, Object> obterEstatisticas() {
        Map<String, Object> estatisticas = new HashMap<>();
        
        estatisticas.put("totalClientes", clienteRepository.count());
        estatisticas.put("clientesAtivos", clienteRepository.countByAtivoTrue());
        estatisticas.put("clientesInativos", clienteRepository.countByAtivoFalse());
        estatisticas.put("aniversariantesHoje", clientesAniversarioHoje().size());
        estatisticas.put("clientesRecentes", clientesRecentes().size());
        
        return estatisticas;
    }
}