package one.digitalinnovation.projects.mathsphysis.beerstockdio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerAlreadyRegisteredException extends Exception {
    public BeerAlreadyRegisteredException(String name) {
        super("Beer already registered with name: " + name);
    }
}
