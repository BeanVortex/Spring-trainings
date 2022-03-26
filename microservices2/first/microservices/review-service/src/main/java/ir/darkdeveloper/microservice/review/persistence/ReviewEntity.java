package ir.darkdeveloper.microservice.review.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.GeneratedValue;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Version
    private Integer version;

    private Integer productId;
    private Integer reviewId;
    private String author;
    private String subject;
    private String content;

    public ReviewEntity(Integer productId, Integer reviewId, String author, String subject, String content) {
        this.productId = productId;
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
    }

}
