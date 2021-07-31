package one.digitalinnovation.projects.mathsphysis.beerstock.exception;

public class BeerAlreadyRegisteredException extends Throwable{
    public BeerAlreadyRegisteredException(String name) {
        super("Beer already registered with name: " + name);
    }
}
