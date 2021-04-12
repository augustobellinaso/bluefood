package augustobellinaso.bluefood.application.service;

import augustobellinaso.bluefood.domain.pagamento.DadosCartao;
import augustobellinaso.bluefood.domain.pagamento.Pagamento;
import augustobellinaso.bluefood.domain.pagamento.PagamentoRepository;
import augustobellinaso.bluefood.domain.pagamento.StatusPagamento;
import augustobellinaso.bluefood.domain.pedido.*;
import augustobellinaso.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Value("${bluefood.sbpay.url}")
    private String sbPayUrl;

    @Value("${bluefood.sbpay.token}")
    private String sbPayToken;

    @Transactional(rollbackFor = PagamentoException.class)
    public Pedido criarEPagar(Carrinho carrinho, String numCartao) throws PagamentoException {

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

        DadosCartao dadosCartao = new DadosCartao();
        dadosCartao.setNumCartao(numCartao);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Token", sbPayToken);

        HttpEntity<DadosCartao> requestEntity = new HttpEntity<>(dadosCartao, headers);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> response;

        try {
            response = restTemplate.postForObject(sbPayUrl, requestEntity, Map.class);

        } catch (Exception e) {
            throw new PagamentoException("Erro no servidor de pagamento");
        }

        StatusPagamento statusPagamento = StatusPagamento.valueOf(response.get("status"));

        if (statusPagamento != StatusPagamento.Autorizado) {
            throw new PagamentoException(statusPagamento.getDescricao());
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setData(LocalDateTime.now());
        pagamento.setPedido(pedido);
        pagamento.definirNumeroEBandeira(numCartao);
        pagamentoRepository.save(pagamento);


        return pedido;
    }
}
