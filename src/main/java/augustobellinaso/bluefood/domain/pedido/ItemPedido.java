package augustobellinaso.bluefood.domain.pedido;

import augustobellinaso.bluefood.domain.restaurante.ItemCardapio;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {

    @EqualsAndHashCode.Include
    private Integer id;

    private ItemCardapio itemCardapio;

    private Integer quantidade;

    private String observacoes;

    private BigDecimal preco;

}
