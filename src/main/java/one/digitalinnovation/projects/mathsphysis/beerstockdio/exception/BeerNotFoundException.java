package one.digitalinnovation.projects.mathsphysis.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeerNotFoundException extends Throwable {
    public BeerNotFoundException(String name) {
        super("Beer not found with name: " + name);
    }

    public BeerNotFoundException(Long id) {
        super("Beer not found with id: " + id);
    }
}
