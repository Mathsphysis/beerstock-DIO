package one.digitalinnovation.projects.mathsphysis.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerAlreadyRegisteredException extends Throwable{
    public BeerAlreadyRegisteredException(String name) {
        super("Beer already registered with name: " + name);
    }
}
