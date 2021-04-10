package augustobellinaso.bluefood.application.service;

import augustobellinaso.bluefood.domain.pedido.*;
import augustobellinaso.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido criarEPagar(Carrinho carrinho, String numCartao){

        //Criando o pedido
        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setCliente(SecurityUtils.loggedCliente());
        pedido.setRestaurante(carrinho.getRestaurante());
        pedido.setStatus(Pedido.Status.Producao);
        pedido.setTaxaEntrega(carrinho.getRestaurante().getTaxaEntrega());
        pedido.setSubtotal(carrinho.getPrecoTotal(false));
        pedido.setTotal(carrinho.getPrecoTotal(true));

        pedidoRepository.save(pedido);

        //Armazenando os itens do carrinho ao pedido
        int ordem = 1;
        for (ItemPedido itemPedido : carrinho.getItens()) {
            itemPedido.setId(new ItemPedidoPK(pedido, ordem++));
            itemPedidoRepository.save(itemPedido);
        }

        //TODO: pagamento

        return pedido;
    }
}
