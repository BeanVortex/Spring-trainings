package ir.darkdeveloper.ratingsdataservice.model;

import ir.darkdeveloper.ratingsdataservice.entity.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRating {

    private List<Rating> userRating;
}
