package augustobellinaso.bluefood.domain.restaurante;

import lombok.Data;

@Data
public class SearchFilter {

    public enum SearchType {
        Texto, Categoria;
    }

    public enum Order {
        Taxa, Tempo;
    }

    public enum Command {
        EntregaGratis, MaiorTaxa, MenorTaxa, MaiorTempo, MenorTempo;
    }

    private String texto;
    private SearchType searchType;
    private Integer categoriaId;
    private Order order = Order.Taxa;
    private boolean asc;
    private boolean entregaGratis;

    public void processFilter() {

        if (searchType == SearchType.Texto) {
            categoriaId = null;
        } else if(searchType == SearchType.Categoria) {
            texto = null;
        }
    }
}
