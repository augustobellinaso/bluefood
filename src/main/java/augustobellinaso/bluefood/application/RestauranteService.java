package augustobellinaso.bluefood.application;

import augustobellinaso.bluefood.domain.restaurante.Restaurante;
import augustobellinaso.bluefood.domain.restaurante.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public void saveRestaurante(Restaurante restaurante) throws ValidationException {
        if (!validateEmail(restaurante.getEmail(), restaurante.getId())) {
            throw new ValidationException("O e-mail já está cadastrado");
        }

        restauranteRepository.save(restaurante);
    }


    private boolean validateEmail(String email, Integer id) {
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
