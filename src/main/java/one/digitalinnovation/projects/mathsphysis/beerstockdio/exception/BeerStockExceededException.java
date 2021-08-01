package one.digitalinnovation.projects.mathsphysis.beerstockdio.exception;

public class BeerStockExceededException extends Exception {
    public BeerStockExceededException(Long id, Integer quantityToIncrement) {
        super(String.format("Beer with id %d has exceeded max stock when tried to increment by %d", id, quantityToIncrement));
    }
}
