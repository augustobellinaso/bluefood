package augustobellinaso.bluefood.infrastructure.web.controller;

import augustobellinaso.bluefood.domain.pedido.Carrinho;
import augustobellinaso.bluefood.domain.pedido.RestauranteDiferenteException;
import augustobellinaso.bluefood.domain.restaurante.ItemCardapio;
import augustobellinaso.bluefood.domain.restaurante.ItemCardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/cliente/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @ModelAttribute("carrinho")
    public Carrinho carrinho() {
        return new Carrinho();
    }

    @GetMapping(path = "/visualizar")
    public String viewCarrinho() {
        return "cliente-carrinho";
    }

    @GetMapping(path = "/adicionar")
    public String adicionarItem(
            @RequestParam("itemId") Integer itemId,
            @RequestParam("quantidade") Integer quantidade,
            @RequestParam("observacoes") String observacoes,
            @ModelAttribute("carrinho") Carrinho carrinho,
            Model model) {

        ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow();

        try {
            carrinho.adicionarItem(itemCardapio, quantidade, observacoes);
        } catch (RestauranteDiferenteException e) {
            model.addAttribute("msg", "Não é possível misturar comidas de restaurantes diferentes!");
        }

        return "cliente-carrinho";
    }


    @GetMapping(path = "/remover")
    public String removerItem(
            @RequestParam("itemId") Integer itemId,
            @ModelAttribute("carrinho") Carrinho carrinho,
            SessionStatus sessionStatus,
            Model model) {

        ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow();

        carrinho.removerItem(itemCardapio);

        if (carrinho.vazio()) {
            sessionStatus.setComplete();
        }

        return "cliente-carrinho";

    }


}
