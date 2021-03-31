package augustobellinaso.bluefood.application.test;

import augustobellinaso.bluefood.domain.cliente.Cliente;
import augustobellinaso.bluefood.domain.cliente.ClienteRepository;
import augustobellinaso.bluefood.domain.restaurante.CategoriaRestaurante;
import augustobellinaso.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import augustobellinaso.bluefood.domain.restaurante.Restaurante;
import augustobellinaso.bluefood.domain.restaurante.RestauranteRepository;
import augustobellinaso.bluefood.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        clientes();
        Restaurante[] restaurantes = restaurantes();
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
}
