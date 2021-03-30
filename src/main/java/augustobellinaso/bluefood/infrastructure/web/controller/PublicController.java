package augustobellinaso.bluefood.infrastructure.web.controller;

import augustobellinaso.bluefood.application.ClienteService;
import augustobellinaso.bluefood.application.RestauranteService;
import augustobellinaso.bluefood.application.ValidationException;
import augustobellinaso.bluefood.domain.cliente.Cliente;
import augustobellinaso.bluefood.domain.restaurante.CategoriaRestaurante;
import augustobellinaso.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import augustobellinaso.bluefood.domain.restaurante.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.ldap.Control;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/public")
public class PublicController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @GetMapping(path = "/cliente/new")
    public String newCliente(Model model){
        model.addAttribute("cliente", new Cliente());
        ControllerHelper.setEditMode(model, false);
        return "cliente-cadastro";
    }

    @GetMapping(path = "/restaurante/new")
    public String newRestaurante(Model model){
        model.addAttribute("restaurante", new Restaurante());
        ControllerHelper.setEditMode(model, false);
        ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
        return "restaurante-cadastro";
    }

    @PostMapping(path = "/cliente/save")
    public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente,
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

    @PostMapping(path = "/restaurante/save")
    public String saveRestaurante(@ModelAttribute("restaurante") @Valid Restaurante restaurante,
                              Errors errors, Model model){

        if (!errors.hasErrors()){

            try {
                restauranteService.saveRestaurante(restaurante);
                model.addAttribute("msg", "Restaurante cadastrado com sucesso!");
            } catch (ValidationException e) {
                errors.rejectValue("email", null, e.getMessage());
            }
        }

        ControllerHelper.setEditMode(model, false);
        return "restaurante-cadastro";
    }
}
