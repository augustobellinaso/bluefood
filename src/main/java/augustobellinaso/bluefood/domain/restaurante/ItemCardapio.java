package augustobellinaso.bluefood.domain.restaurante;

import augustobellinaso.bluefood.infrastructure.web.validator.UploadConstraint;
import augustobellinaso.bluefood.util.FileType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "item_cardapio")
public class ItemCardapio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome não pode ser vazio")
    @Size(max = 50)
    private String nome;

    @NotBlank(message = "A categoria não pode ser vazia")
    @Size(max = 25)
    private String categoria;

    @NotBlank(message = "A descricao não pode ser vazia")
    @Size(max = 80)
    private String descricao;

    @Size(max = 50)
    private String imagem;

    @NotNull(message = "O preço não pode ser vazio")
    @Min(0)
    private BigDecimal preco;

    @NotNull
    private Boolean destaque;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @UploadConstraint(acceptedTypes = FileType.PNG, message = "O arquivo não é um arquivo de imagem válido")
    private MultipartFile imagemFile;

    public void setImagemFileName(){
        if (getId() == null) {
            throw new IllegalStateException("O objeto precisa primeiro ser criado");
        }

        this.imagem = String.format("%04d-comida.%s", getId(), FileType.of(imagemFile.getContentType()).getExtension());
    }

}
