package augustobellinaso.bluefood.repository;

import augustobellinaso.bluefood.domain.restaurante.CategoriaRestaurante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CategoriaRestauranteRepository2Test {

    @Autowired
    private TestEntityManager em;

    @Test
    public void testInsertAndDelete() throws Exception {

        assertThat(em).isNotNull(); //testa se criou a categoria

        CategoriaRestaurante cr = new CategoriaRestaurante();
        cr.setNome("Chinesa");
        cr.setImagem("chinesa.png");

        em.persistAndFlush(cr); //grava no banco de dados e imediatamente insere no BD
        //sem aguardar o fim da transação

        assertThat(cr.getId()).isNotNull(); //testa se criou um ID

        CategoriaRestaurante cr2 = em.find(CategoriaRestaurante.class, cr.getId());
        assertThat(cr.getNome()).isEqualTo(cr2.getNome()); //testa se os nomes são iguais

        em.remove(cr);

        assertThat(em.find(CategoriaRestaurante.class, cr.getId())).isNull();
    }
}
