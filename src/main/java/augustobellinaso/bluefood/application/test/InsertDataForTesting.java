package augustobellinaso.bluefood.application.test;

import augustobellinaso.bluefood.domain.cliente.Cliente;
import augustobellinaso.bluefood.domain.cliente.ClienteRepository;
import augustobellinaso.bluefood.domain.pedido.Pedido;
import augustobellinaso.bluefood.domain.pedido.PedidoRepository;
import augustobellinaso.bluefood.domain.restaurante.*;
import augustobellinaso.bluefood.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InsertDataForTesting {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Cliente[] clientes = clientes();
        Restaurante[] restaurantes = restaurantes();
        itensCardapio(restaurantes);

        Pedido p = new Pedido();
        p.setData(LocalDateTime.now());
        p.setCliente(clientes[0]);
        p.setRestaurante(restaurantes[0]);
        p.setStatus(Pedido.Status.Producao);
        p.setSubtotal(BigDecimal.valueOf(10));
        p.setTaxaEntrega(BigDecimal.valueOf(2));
        p.setTotal(BigDecimal.valueOf(12));
        pedidoRepository.save(p);
    }

    private Restaurante[] restaurantes() {
        List<Restaurante> restaurantes = new ArrayList<>();

        CategoriaRestaurante categoriaPizza = categoriaRestauranteRepository.findById(1).orElseThrow();
        CategoriaRestaurante categoriaSanduiche = categoriaRestauranteRepository.findById(2).orElseThrow();
        CategoriaRestaurante categoriaSobremesa = categoriaRestauranteRepository.findById(5).orElseThrow();
        CategoriaRestaurante categoriaJapones = categoriaRestauranteRepository.findById(6).orElseThrow();

        Restaurante r = new Restaurante();
        r.setNome("Bubger King");
        r.setEmail("r1@bluefood.com.br");
        r.setSenha(StringUtils.encrypt("r"));
        r.setCnpj("00000000000011");
        r.setTaxaEntrega(BigDecimal.valueOf(3.2));
        r.setTempoEntregaBase(30);
        r.setTelefone("99999999999");
        r.getCategorias().add(categoriaSanduiche);
        r.getCategorias().add(categoriaSobremesa);
        r.setLogotipo("0001-logo.png");
        restauranteRepository.save(r);
        restaurantes.add(r);

        r = new Restaurante();
        r.setNome("Mc Naldo's");
        r.setEmail("r2@bluefood.com.br");
        r.setSenha(StringUtils.encrypt("r"));
        r.setCnpj("00000000000022");
        r.setTaxaEntrega(BigDecimal.valueOf(34.5));
        r.setTempoEntregaBase(50);
        r.setTelefone("11111111111");
        r.getCategorias().add(categoriaSanduiche);
        r.getCategorias().add(categoriaSobremesa);
        r.setLogotipo("0002-logo.png");
        restauranteRepository.save(r);
        restaurantes.add(r);

        r = new Restaurante();
        r.setNome("Sbubby");
        r.setEmail("r3@bluefood.com.br");
        r.setSenha(StringUtils.encrypt("r"));
        r.setCnpj("00000000000033");
        r.setTaxaEntrega(BigDecimal.valueOf(3.5));
        r.setTempoEntregaBase(50);
        r.setTelefone("55555555555");
        r.getCategorias().add(categoriaSanduiche);
        r.getCategorias().add(categoriaSobremesa);
        r.getCategorias().add(categoriaJapones);
        r.setLogotipo("0003-logo.png");
        restauranteRepository.save(r);
        restaurantes.add(r);

        r = new Restaurante();
        r.setNome("Pizza Brut");
        r.setEmail("r4@bluefood.com.br");
        r.setSenha(StringUtils.encrypt("r"));
        r.setCnpj("00000000000044");
        r.setTaxaEntrega(BigDecimal.valueOf(4.5));
        r.setTempoEntregaBase(10);
        r.setTelefone("33333333333");
        r.getCategorias().add(categoriaPizza);
        r.getCategorias().add(categoriaSobremesa);
        r.setLogotipo("0004-logo.png");
        restauranteRepository.save(r);
        restaurantes.add(r);

        r = new Restaurante();
        r.setNome("Wiki Japa");
        r.setEmail("r5@bluefood.com.br");
        r.setSenha(StringUtils.encrypt("r"));
        r.setCnpj("00000000000105");
        r.setTaxaEntrega(BigDecimal.valueOf(14.9));
        r.setTelefone("99876671014");
        r.getCategorias().add(categoriaJapones);
        r.getCategorias().add(categoriaSobremesa);
        r.setLogotipo("0005-logo.png");
        r.setTempoEntregaBase(19);
        restauranteRepository.save(r);
        restaurantes.add(r);

        Restaurante[] array = new Restaurante[restaurantes.size()];
        return restaurantes.toArray(array);
    }

    private Cliente[] clientes() {
        List<Cliente> clientes = new ArrayList<>();

        Cliente c = new Cliente();
        c.setNome("João Silva");
        c.setEmail("joao@bluefood.com.br");
        c.setSenha(StringUtils.encrypt("c"));
        c.setCpf("22222222222");
        c.setCep("00000000");
        c.setTelefone("99999999999");
        clienteRepository.save(c);
        clientes.add(c);

        c = new Cliente();
        c.setNome("Maria Torres");
        c.setEmail("maria@bluefood.com.br");
        c.setSenha(StringUtils.encrypt("c"));
        c.setCpf("33333333333");
        c.setCep("12345678");
        c.setTelefone("12345678900");
        clienteRepository.save(c);
        clientes.add(c);

        Cliente[] array = new Cliente[clientes.size()];
        return clientes.toArray(array);
    }

    private void itensCardapio(Restaurante[] restaurantes) {
        ItemCardapio ic = new ItemCardapio();
        ic.setCategoria("Sanduíche");
        ic.setDescricao("Delicioso sanduíche com molho");
        ic.setNome("Double Cheese Burger Special");
        ic.setPreco(BigDecimal.valueOf(23.8));
        ic.setRestaurante(restaurantes[0]);
        ic.setDestaque(true);
        ic.setImagem("0001-comida.png");
        itemCardapioRepository.save(ic);

        ic = new ItemCardapio();
        ic.setCategoria("Sanduíche");
        ic.setDescricao("Sanduíche padrão que mata a fome");
        ic.setNome("Cheese Burger Simples");
        ic.setPreco(BigDecimal.valueOf(17.8));
        ic.setRestaurante(restaurantes[0]);
        ic.setDestaque(false);
        ic.setImagem("0006-comida.png");
        itemCardapioRepository.save(ic);

        ic = new ItemCardapio();
        ic.setCategoria("Sanduíche");
        ic.setDescricao("Sanduíche natural com peito de peru");
        ic.setNome("Sanduíche natural da Casa");
        ic.setPreco(BigDecimal.valueOf(11.8));
        ic.setRestaurante(restaurantes[0]);
        ic.setDestaque(false);
        ic.setImagem("0007-comida.png");
        itemCardapioRepository.save(ic);

        ic = new ItemCardapio();
        ic.setCategoria("Bebida");
        ic.setDescricao("Refrigerante com gás");
        ic.setNome("Refrigerante Tradicional");
        ic.setPreco(BigDecimal.valueOf(9));
        ic.setRestaurante(restaurantes[0]);
        ic.setDestaque(false);
        ic.setImagem("0004-comida.png");
        itemCardapioRepository.save(ic);

        ic = new ItemCardapio();
        ic.setCategoria("Bebida");
        ic.setDescricao("Suco natural e docinho");
        ic.setNome("Suco de Laranja");
        ic.setPreco(BigDecimal.valueOf(9));
        ic.setRestaurante(restaurantes[0]);
        ic.setDestaque(false);
        ic.setImagem("0005-comida.png");
        itemCardapioRepository.save(ic);

        ic = new ItemCardapio();
        ic.setCategoria("Pizza");
        ic.setDescricao("Pizza saborosa com cebelo");
        ic.setNome("Pizza de Calabresa");
        ic.setPreco(BigDecimal.valueOf(38.9));
        ic.setRestaurante(restaurantes[3]);
        ic.setDestaque(false);
        ic.setImagem("0002-comida.png");
        itemCardapioRepository.save(ic);

        ic = new ItemCardapio();
        ic.setCategoria("Japonesa");
        ic.setDescricao("Delicioso Uramaki Tradicional");
        ic.setNome("Uramaki");
        ic.setPreco(BigDecimal.valueOf(16.8));
        ic.setRestaurante(restaurantes[4]);
        ic.setDestaque(false);
        ic.setImagem("0003-comida.png");
        itemCardapioRepository.save(ic);

    }

}
