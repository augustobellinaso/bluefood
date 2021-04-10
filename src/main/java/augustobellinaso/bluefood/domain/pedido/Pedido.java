package augustobellinaso.bluefood.domain.pedido;

import augustobellinaso.bluefood.domain.cliente.Cliente;
import augustobellinaso.bluefood.domain.restaurante.Restaurante;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido implements Serializable {

    public enum Status {
        Producao(1, "Pedido em produção", false),
        Entrega(2, "Saiu para entrega", false),
        Concluido(3, "Pedido concluído", true);

        Status(int ordem, String descricao, boolean ultimo) {
            this.ordem = ordem;
            this.descricao = descricao;
            this.ultimo = ultimo;
        }
        int ordem;
        String descricao;
        boolean ultimo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDateTime data;

    @NotNull
    private Status status;

    @ManyToOne
    @NotNull
    private Cliente cliente;

    @ManyToOne
    @NotNull
    private Restaurante restaurante;

    @NotNull
    private BigDecimal subtotal;

    @NotNull
    private BigDecimal total;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens;


}
