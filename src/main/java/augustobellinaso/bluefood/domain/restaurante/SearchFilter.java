package augustobellinaso.bluefood.domain.restaurante;

import lombok.Data;

@Data
public class SearchFilter {

    public enum SearchType {
        Texto, Categoria
    }

    private String texto;
    private SearchType searchType;
    private Integer categoriaId;
}
