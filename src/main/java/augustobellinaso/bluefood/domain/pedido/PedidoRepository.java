package augustobellinaso.bluefood.domain.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM Pedido p WHERE p.cliente.id = ?1 ORDER BY p.data DESC")
    public List<Pedido> listPedidosByCliente(Integer clienteId);

    //Usando o padrão de nome da JPA
    //public List<Pedido> findByCliente_Id(Integer clienteId);
}
