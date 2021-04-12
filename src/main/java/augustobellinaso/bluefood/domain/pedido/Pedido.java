package augustobellinaso.bluefood.domain.pedido;

import augustobellinaso.bluefood.domain.cliente.Cliente;
import augustobellinaso.bluefood.domain.pagamento.Pagamento;
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
        Producao(1, "Em produção", false),
        Entrega(2, "Saiu para entrega", false),
        Concluido(3, "Concluído", true);

        Status(int ordem, String descricao, boolean ultimo) {
            this.ordem = ordem;
            this.descricao = descricao;
            this.ultimo = ultimo;
        }
        int ordem;
        String descricao;
        boolean ultimo;

        public String getDescricao() {
            return descricao;
        }

        public int getOrdem() {
            return ordem;
        }

        public boolean isUltimo() {
            return ultimo;
        }

        public static Status fromOrdem(int ordem){
            for (Status status : Status.values()) {
                if (status.getOrdem() == ordem) {
                    return status;
                }
            }

            return null;
        }
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
    @Column(name = "taxa_entrega")
    private BigDecimal taxaEntrega;

    @NotNull
    private BigDecimal total;

    @OneToMany(mappedBy = "id.pedido", fetch = FetchType.EAGER)
    private Set<ItemPedido> itens;

    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;

    public String getFormattedId() {
        return String.format("#%04d", id);
    }

    public void definirProximoStatus(){
        int ordem = status.getOrdem();

        Status newStatus = Status.fromOrdem(ordem + 1);

        if (newStatus != null) {
            this.status = newStatus;
        }
    }

}
