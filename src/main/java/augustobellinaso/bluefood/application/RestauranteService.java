package augustobellinaso.bluefood.application;

import augustobellinaso.bluefood.domain.cliente.Cliente;
import augustobellinaso.bluefood.domain.cliente.ClienteRepository;
import augustobellinaso.bluefood.domain.restaurante.Restaurante;
import augustobellinaso.bluefood.domain.restaurante.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

}
