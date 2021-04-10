package augustobellinaso.bluefood.domain.pedido;

import augustobellinaso.bluefood.domain.restaurante.ItemCardapio;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private ItemPedidoPK id;

    @ManyToOne
    @NotNull
    private ItemCardapio itemCardapio;

    @NotNull
    private Integer quantidade;

    @Size(max = 100)
    private String observacoes;

    @NotNull
    private BigDecimal preco;

    public BigDecimal getPrecoCalculado() {
        return preco.multiply(BigDecimal.valueOf(quantidade));
    }

}
