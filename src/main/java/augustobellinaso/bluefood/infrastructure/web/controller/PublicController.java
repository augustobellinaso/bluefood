package augustobellinaso.bluefood.infrastructure.web.controller;

import augustobellinaso.bluefood.application.ClienteService;
import augustobellinaso.bluefood.domain.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/public")
public class PublicController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(path = "/cliente/new")
    public String newCliente(Model model){
        model.addAttribute("cliente", new Cliente());
        ControllerHelper.setEditMode(model, false);
        return "cliente-cadastro";
    }

    @PostMapping(path = "/cliente/save")
    public String saveCliente(@ModelAttribute("cliente") Cliente cliente){
        clienteService.saveCliente(cliente);

        return "cliente-cadastro";
    }
}
