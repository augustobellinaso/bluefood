package augustobellinaso.bluefood.repository;

import static org.assertj.core.api.Assertions.assertThat;

import augustobellinaso.bluefood.domain.restaurante.CategoriaRestaurante;
import augustobellinaso.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class CategoriaRestauranteRepositoryTest {

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @Test
    public void testInsertAndDelete() throws Exception {

        assertThat(categoriaRestauranteRepository).isNotNull(); //testa se criou a categoria

        CategoriaRestaurante cr = new CategoriaRestaurante();
        cr.setNome("Chinesa");
        cr.setImagem("chinesa.png");

        categoriaRestauranteRepository.saveAndFlush(cr); //grava no banco de dados e imediatamente insere no BD
        //sem aguardar o fim da transação

        assertThat(cr.getId()).isNotNull(); //testa se criou um ID

        CategoriaRestaurante cr2 = categoriaRestauranteRepository.findById(cr.getId()).orElseThrow();
        assertThat(cr.getNome()).isEqualTo(cr2.getNome()); //testa se os nomes são iguais

        assertThat(categoriaRestauranteRepository.findAll()).hasSize(7); //vê se realmente adicionou

        categoriaRestauranteRepository.delete(cr);

        assertThat(categoriaRestauranteRepository.findAll()).hasSize(6); //vê se excluiu o registro


    }
}
