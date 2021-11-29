package ir.darkdeveloper.ratingsdataservice.repo;

import ir.darkdeveloper.ratingsdataservice.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepo extends JpaRepository<Rating, String> {
}
