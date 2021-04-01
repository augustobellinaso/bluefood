package augustobellinaso.bluefood.infrastructure.web.controller;

import augustobellinaso.bluefood.application.service.ClienteService;
import augustobellinaso.bluefood.application.service.ValidationException;
import augustobellinaso.bluefood.domain.cliente.Cliente;
import augustobellinaso.bluefood.domain.cliente.ClienteRepository;
import augustobellinaso.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @GetMapping(path = "/home")
    public String home(){
        return "cliente-home";
    }

    @GetMapping(path = "/edit")
    public String edit(Model model) {
        Integer clienteId = SecurityUtils.loggedCliente().getId();
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        model.addAttribute("cliente", cliente);
        ControllerHelper.setEditMode(model, true);
        return "cliente-cadastro";
    }

    @PostMapping(path = "/save")
    public String save(@ModelAttribute("cliente") @Valid Cliente cliente,
                       Errors errors, Model model){

        if (!errors.hasErrors()){

            try {
                clienteService.saveCliente(cliente);
                model.addAttribute("msg", "Cliente cadastrado com sucesso!");
            } catch (ValidationException e) {
                errors.rejectValue("email", null, e.getMessage());
            }
        }

        ControllerHelper.setEditMode(model, false);
        return "cliente-cadastro";
    }

}