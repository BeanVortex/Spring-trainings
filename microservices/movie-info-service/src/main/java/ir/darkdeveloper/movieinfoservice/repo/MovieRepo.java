package ir.darkdeveloper.movieinfoservice.repo;

import ir.darkdeveloper.movieinfoservice.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends JpaRepository<Movie, String> {
}
