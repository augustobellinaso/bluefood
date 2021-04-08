package augustobellinaso.bluefood.application.service;

import augustobellinaso.bluefood.domain.cliente.Cliente;
import augustobellinaso.bluefood.domain.cliente.ClienteRepository;
import augustobellinaso.bluefood.domain.restaurante.Restaurante;
import augustobellinaso.bluefood.domain.restaurante.RestauranteComparator;
import augustobellinaso.bluefood.domain.restaurante.RestauranteRepository;
import augustobellinaso.bluefood.domain.restaurante.SearchFilter;
import augustobellinaso.bluefood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;


@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ImageService imageService;

    @Transactional
    public void saveRestaurante(Restaurante restaurante) throws ValidationException {
        if (!validateEmail(restaurante.getEmail(), restaurante.getId())) {
            throw new ValidationException("O e-mail já está cadastrado");
        }

        if (restaurante.getId() != null) {
            Restaurante restauranteDB = restauranteRepository.findById(restaurante.getId()).orElseThrow();
            restaurante.setSenha(restauranteDB.getSenha());
        } else {
            restaurante.encryptPassword();
            restaurante = restauranteRepository.save(restaurante);
            restaurante.setLogotipoFileName();
            imageService.uploadLogotipo(restaurante.getLogotipoFile(), restaurante.getLogotipo());

        }
    }


    private boolean validateEmail(String email, Integer id) {
        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente != null) {
            return false;
        }

        Restaurante restaurante = restauranteRepository.findByEmail(email);

        if (restaurante != null) {
            if (id == null) {
                return false;
            }

            if (!restaurante.getId().equals(id)){
                return false;
            }
        }

        return true;
    }

    public List<Restaurante> search(SearchFilter filter) {
        List<Restaurante> restaurantes;

        if (filter.getSearchType() == SearchFilter.SearchType.Texto) {
            restaurantes = restauranteRepository.findByNomeIgnoreCaseContaining(filter.getTexto());

        } else if (filter.getSearchType() == SearchFilter.SearchType.Categoria){
            restaurantes = restauranteRepository.findByCategorias_Id(filter.getCategoriaId());

        } else {
            throw new IllegalStateException("O tipo de busca " + filter.getSearchType() + " não é suportado!");
        }

        Iterator<Restaurante> it = restaurantes.iterator();

        while (it.hasNext()) {
            Restaurante restaurante = it.next();
            double taxaEntrega = restaurante.getTaxaEntrega().doubleValue();

            if (filter.isEntregaGratis() && taxaEntrega > 0
                    || !filter.isEntregaGratis() && taxaEntrega == 0) {
                it.remove();
            }
        }

        RestauranteComparator comparator = new RestauranteComparator(filter, SecurityUtils.loggedCliente().getCep());
        restaurantes.sort(comparator);

        return restaurantes;
    }

}
