package augustobellinaso.bluefood.application.service;

import augustobellinaso.bluefood.domain.pedido.Pedido;
import augustobellinaso.bluefood.domain.pedido.PedidoRepository;
import augustobellinaso.bluefood.domain.pedido.RelatorioPedidoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listPedidos(Integer restauranteId, RelatorioPedidoFilter filter){

        Integer pedidoId = filter.getPedidoId();

        if (pedidoId != null) {
            Pedido pedido = pedidoRepository.finbByIdAndRestaurante_Id(pedidoId, restauranteId);
            return List.of(pedido);
        }

        LocalDate dataInicial = filter.getDataInicial();
        LocalDate dataFinal = filter.getDataFinal();

        if (dataInicial == null) {
            return List.of();
        }

        if (dataFinal == null) {
            dataFinal = LocalDate.now();
        }

        return pedidoRepository.findByDateInterval(restauranteId, dataInicial.atStartOfDay(), dataFinal.atTime(23, 59, 59));

    }
}
