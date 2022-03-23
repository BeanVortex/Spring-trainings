package ir.darkdeveloper.microservice.recommendation.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("recommendations")
@CompoundIndex(name = "prod-rec-id", unique = true, def = "{'productId': 1, 'recommendationId': 1}")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RecommendationEntity {

    @Id
    private String id;

    @Version
    private Integer version;

    private Integer productId;
    private Integer recommendationId;
    private String author;
    private Integer rating;
    private String content;

    public RecommendationEntity(Integer productId, Integer recommendationId,
                                String author, Integer rating, String content) {
        this.productId = productId;
        this.recommendationId = recommendationId;
        this.author = author;
        this.rating = rating;
        this.content = content;
    }

}
