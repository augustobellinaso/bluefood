package augustobellinaso.bluefood.infrastructure.web.controller;

import augustobellinaso.bluefood.domain.cliente.Cliente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/public")
public class PublicController {

    @GetMapping(path = "/cliente/new")
    public String newCliente(Model model){
        model.addAttribute("cliente", new Cliente());
        return "cliente-cadastro";
    }
}
