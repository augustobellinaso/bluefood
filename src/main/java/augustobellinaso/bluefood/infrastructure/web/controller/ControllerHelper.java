package augustobellinaso.bluefood.infrastructure.web.controller;

import augustobellinaso.bluefood.domain.restaurante.CategoriaRestaurante;
import augustobellinaso.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import java.util.List;

public class ControllerHelper {

    public static void setEditMode(Model model, boolean isEdit){
        model.addAttribute("editMode", isEdit);
    }

    public static void addCategoriasToRequest(CategoriaRestauranteRepository repository, Model model) {

        List<CategoriaRestaurante> categorias = repository.findAll(Sort.by("nome"));
        model.addAttribute("categorias", categorias);
    }
}
