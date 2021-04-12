package augustobellinaso.bluefood.infrastructure.web.controller;

import augustobellinaso.bluefood.application.service.RestauranteService;
import augustobellinaso.bluefood.application.service.ValidationException;
import augustobellinaso.bluefood.domain.restaurante.*;
import augustobellinaso.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/restaurante")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @GetMapping(path = "/home")
    public String home() {
        return "restaurante-home";
    }

    @GetMapping(path = "/edit")
    public String edit(Model model) {
        Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
        Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
        model.addAttribute("restaurante", restaurante);

        ControllerHelper.setEditMode(model, true);
        ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
        return "restaurante-cadastro";
    }

    @PostMapping(path = "/save")
    public String save(@ModelAttribute("restaurante") @Valid Restaurante restaurante,
                       Errors errors, Model model){

        if (!errors.hasErrors()){

            try {
                restauranteService.saveRestaurante(restaurante);
                model.addAttribute("msg", "Restaurante gravado com sucesso!");
            } catch (ValidationException e) {
                errors.rejectValue("email", null, e.getMessage());
            }
        }

        ControllerHelper.setEditMode(model, false);
        ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
        return "restaurante-cadastro";
    }

    @GetMapping(path = "/comidas")
    public String viewComidas(Model model){
        Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
        Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
        model.addAttribute("restaurante", restaurante);

        List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
        model.addAttribute("itensCardapio", itensCardapio);

        model.addAttribute("itemCardapio", new ItemCardapio());


        return "restaurante-comidas";
    }

    @GetMapping(path = "/comidas/remover")
    public String remover(@RequestParam("itemId") Integer itemId,
                        Model model){

        itemCardapioRepository.deleteById(itemId);
        return "redirect:/restaurante/comidas";
    }

    @PostMapping(path = "/comidas/cadastrar")
    public String cadastrar(@Valid @ModelAttribute("itemCardapio") ItemCardapio itemCardapio,
                            Errors errors,
                            Model model){

        if (errors.hasErrors()) {
            Integer restauranteId = SecurityUtils.loggedRestaurante().getId();
            Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
            model.addAttribute("restaurante", restaurante);

            List<ItemCardapio> itensCardapio = itemCardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
            model.addAttribute("itensCardapio", itensCardapio);

            return "restaurante-comidas";
        }

        restauranteService.saveItemCardapio(itemCardapio);
        return "redirect:/restaurante/comidas";
    }
}
