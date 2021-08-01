package one.digitalinnovation.projects.mathsphysis.beerstockdio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerNegativeStockException extends Exception {
    public BeerNegativeStockException(Long id, Integer quantityToDecrement) {
        super(String.format("Beer with id %d has yielded negative stock when tried to decrement by %d", id, quantityToDecrement));
    }
}
