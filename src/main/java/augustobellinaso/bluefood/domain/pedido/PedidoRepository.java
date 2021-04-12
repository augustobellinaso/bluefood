package augustobellinaso.bluefood.domain.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = ?1 ORDER BY p.data DESC")
    public List<Pedido> listPedidosByCliente(Integer clienteId);

    //Usando o padrão de nome da JPA
    //public List<Pedido> findByCliente_Id(Integer clienteId);

    public List<Pedido> findByRestaurante_IdOrderByDataDesc(Integer restauranteId);

    public Pedido finbByIdAndRestaurante_Id(Integer pedidoId, Integer restauranteId);

    @Query("SELECT p FROM Pedido p WHERE p.restaurante.id = ?1 AND p.data BETWEEN ?2 AND ?3")
    public List<Pedido> findByDateInterval(Integer restauranteId, LocalDateTime dataInicial, LocalDateTime dataFinal);
}
