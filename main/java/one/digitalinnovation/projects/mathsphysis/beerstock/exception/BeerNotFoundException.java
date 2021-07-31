package one.digitalinnovation.projects.mathsphysis.beerstock.exception;

public class BeerNotFoundException extends Throwable {
    public BeerNotFoundException(String name) {
        super("Beer not found with name: " + name);
    }

    public BeerNotFoundException(Long id) {
        super("Beer not found with id: " + id);
    }
}
