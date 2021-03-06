package augustobellinaso.bluefood.domain.restaurante;

import augustobellinaso.bluefood.domain.usuario.Usuario;
import augustobellinaso.bluefood.infrastructure.web.validator.UploadConstraint;
import augustobellinaso.bluefood.util.FileType;
import augustobellinaso.bluefood.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Table(name="restaurante")
public class Restaurante extends Usuario {

    @NotBlank(message = "O CNPJ não pode ser vazio")
    @Pattern(regexp = "[0-9]{14}")
    @Column(length = 14, nullable = false)
    private String cnpj;

    @Size(max = 80)
    private String logotipo;

    @UploadConstraint(acceptedTypes = FileType.PNG, message = "O arquivo não é um arquivo de imagem válido")
    private transient MultipartFile logotipoFile;

    @NotNull(message = "A taxa de entrega não pode ser vazia")
    @Min(0)
    @Max(99)
    private BigDecimal taxaEntrega;

    @NotNull(message = "O tempo de entrega não pode ser vazio")
    @Min(0)
    @Max(120)
    private Integer tempoEntregaBase;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "restaurante_has_categoria",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_restaurante_id")
    )
    @Size(min = 1, message = "O restaurante precisa ter pelo menos uma categoria")
    @ToString.Exclude
    private Set<CategoriaRestaurante> categorias = new HashSet<>(0);

    @OneToMany(mappedBy = "restaurante")
    private Set<ItemCardapio> itensCardapio = new HashSet<>(0);

    public void setLogotipoFileName(){
        if (getId() == null) {
            throw new IllegalStateException("É preciso primeiro gravar o registro");
        }

        this.logotipo = String.format("%04d-logo.%s", getId(), FileType.of(logotipoFile.getContentType()).getExtension());
    }


    public String getCategoriaAsText() {
        Set<String> strings = new LinkedHashSet<>();

        for (CategoriaRestaurante categoria : categorias) {
            strings.add(categoria.getNome());
        }

        return StringUtils.concatenate(strings);
    }

    public Integer calcularTempoEntrega(String cep) {
        int soma = 0;

        for (char c : cep.toCharArray()) {
            int v = Character.getNumericValue(c);
            if (v > 0) {
                soma += v;
            }
        }

        soma /= 2;

        return tempoEntregaBase + soma;
    }
}
