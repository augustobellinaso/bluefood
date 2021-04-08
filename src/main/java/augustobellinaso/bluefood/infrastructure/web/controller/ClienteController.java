package augustobellinaso.bluefood.infrastructure.web.controller;

import augustobellinaso.bluefood.application.service.ClienteService;
import augustobellinaso.bluefood.application.service.RestauranteService;
import augustobellinaso.bluefood.application.service.ValidationException;
import augustobellinaso.bluefood.domain.cliente.Cliente;
import augustobellinaso.bluefood.domain.cliente.ClienteRepository;
import augustobellinaso.bluefood.domain.restaurante.CategoriaRestaurante;
import augustobellinaso.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import augustobellinaso.bluefood.domain.restaurante.Restaurante;
import augustobellinaso.bluefood.domain.restaurante.SearchFilter;
import augustobellinaso.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping(path = "/home")
    public String home(Model model){
        List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
        model.addAttribute("categorias", categorias);
        model.addAttribute("searchFilter", new SearchFilter());
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

    @GetMapping(path = "/search")
    public String search(@ModelAttribute("searchFilter") SearchFilter filter,
                         @RequestParam(value = "cmd", required = false) String command,
                         Model model) {

        filter.processFilter(command    );

        List<Restaurante> restaurantes = restauranteService.search(filter);
        model.addAttribute("restaurantes", restaurantes);

        ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);

        model.addAttribute("searcFilter", filter);

        return "cliente-busca";
    }

}
