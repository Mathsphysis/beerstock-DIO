package one.digitalinnovation.projects.mathsphysis.beerstockdio.exception;

public class BeerNegativeStockException extends Throwable {
    public BeerNegativeStockException(Long id, Integer quantityToDecrement) {
        super(String.format("Beer with id %d has yielded negative stock when tried to decrement by %d", id, quantityToDecrement));
    }
}
