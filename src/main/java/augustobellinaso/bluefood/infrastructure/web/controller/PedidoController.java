package augustobellinaso.bluefood.infrastructure.web.controller;

import augustobellinaso.bluefood.domain.pedido.Pedido;
import augustobellinaso.bluefood.domain.pedido.PedidoRepository;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente/pedido")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping(path = "/view")
    public String viewPedido(
            @RequestParam("pedidoId") Integer pedidoId,
            Model model) {

        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
        model.addAttribute("pedido", pedido);

        return "cliente-pedido";
    }
}
