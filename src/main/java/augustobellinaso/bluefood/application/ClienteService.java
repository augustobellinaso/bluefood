package augustobellinaso.bluefood.application;

import augustobellinaso.bluefood.domain.cliente.Cliente;
import augustobellinaso.bluefood.domain.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void saveCliente(Cliente cliente){
        clienteRepository.save(cliente);
    }
}
