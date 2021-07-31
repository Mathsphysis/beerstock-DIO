package one.digitalinnovation.projects.mathsphysis.beerstockdio.repository;

import one.digitalinnovation.projects.mathsphysis.beerstockdio.entity.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeerRepository extends JpaRepository<Beer, Long> {
    Optional<Beer> findByName(String name);
}
