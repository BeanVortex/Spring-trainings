package ir.darkdeveloper.microservices.product.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductEntity {

    @Id
    private String id;

    @Version
    private Integer version;

    @Indexed(unique = true)
    private Integer productId;

    private String name;
    private Integer weight;

    public ProductEntity(Integer productId, String name, Integer weight) {
        this.productId = productId;
        this.name = name;
        this.weight = weight;
    }
}


