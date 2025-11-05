package app.projeto.repository;

import app.projeto.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Buscar todos com JOIN FETCH para evitar lazy loading
    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.endereco")
    List<Cliente> findAllWithEndereco();
    
    // Buscar clientes ativos
    List<Cliente> findByAtivoTrue();
    
    // Buscar clientes inativos
    List<Cliente> findByAtivoFalse();
    
    // Buscar por nome (ignora maiúsculas/minúsculas)
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    
    // Buscar por email (ignora maiúsculas/minúsculas)
    Optional<Cliente> findByEmailIgnoreCase(String email);
    
    // Buscar por CPF
    Optional<Cliente> findByCpf(String cpf);
    
    // Buscar por cidade do endereço
    @Query("SELECT c FROM Cliente c WHERE UPPER(c.endereco.cidade) = UPPER(:cidade)")
    List<Cliente> findByEndereco_CidadeIgnoreCase(@Param("cidade") String cidade);
    
    // Buscar aniversariantes (dia e mês)
    @Query("SELECT c FROM Cliente c WHERE DAY(c.dataNascimento) = :dia AND MONTH(c.dataNascimento) = :mes")
    List<Cliente> findByDataNascimentoDayAndMonth(@Param("dia") int dia, @Param("mes") int mes);
    
    // Buscar clientes recentes
    List<Cliente> findByDataCriacaoAfterOrderByDataCriacaoDesc(LocalDateTime dataInicio);
    
    // Contar clientes ativos
    long countByAtivoTrue();
    
    // Contar clientes inativos
    long countByAtivoFalse();
    
    // Buscar por telefone
    Optional<Cliente> findByTelefone(String telefone);
    
    // Buscar clientes por estado
    @Query("SELECT c FROM Cliente c WHERE UPPER(c.endereco.estado) = UPPER(:estado)")
    List<Cliente> findByEnderecoEstado(@Param("estado") String estado);
    
    // Buscar clientes por CEP
    @Query("SELECT c FROM Cliente c WHERE c.endereco.cep = :cep")
    List<Cliente> findByEnderecoCep(@Param("cep") String cep);
    
    // Buscar clientes que fizeram pedidos recentemente
    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.pedidos p WHERE p.dataPedido >= :dataInicio")
    List<Cliente> findClientesComPedidosRecentes(@Param("dataInicio") LocalDateTime dataInicio);
    
    // Buscar top clientes por quantidade de pedidos
    @Query("SELECT c FROM Cliente c JOIN c.pedidos p GROUP BY c ORDER BY COUNT(p) DESC")
    List<Cliente> findTopClientesPorPedidos();
}