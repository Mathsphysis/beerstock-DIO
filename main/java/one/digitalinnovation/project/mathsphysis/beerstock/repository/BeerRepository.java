package one.digitalinnovation.project.mathsphysis.beerstock.repository;

import one.digitalinnovation.project.mathsphysis.beerstock.entity.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeerRepository extends JpaRepository {
    Optional<Beer> findByName(String name);
}
