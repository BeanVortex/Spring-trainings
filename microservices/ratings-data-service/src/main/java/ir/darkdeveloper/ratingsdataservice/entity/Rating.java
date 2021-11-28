package ir.darkdeveloper.ratingsdataservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue
    private Long id;
    private String movieId;
    private int rating;
}
