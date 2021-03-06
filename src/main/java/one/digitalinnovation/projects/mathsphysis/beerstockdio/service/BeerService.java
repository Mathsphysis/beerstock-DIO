package one.digitalinnovation.projects.mathsphysis.beerstockdio.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.dto.request.BeerDTO;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.dto.response.MessageResponseDTO;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.entity.Beer;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerNegativeStockException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerNotFoundException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerStockExceededException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.mapper.BeerMapper;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper = BeerMapper.INSTANCE;

    public BeerDTO createBeer(BeerDTO beerDTO) throws BeerAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(beerDTO.getName());
        Beer beer = beerMapper.toModel(beerDTO);
        Beer savedBeer = beerRepository.save(beer);

        return beerMapper.toDTO(savedBeer);
    }

    public BeerDTO findByName(String name) throws BeerNotFoundException {
        Beer beerFound = beerRepository.findByName(name).orElseThrow(() -> new BeerNotFoundException(name));

        return beerMapper.toDTO(beerFound);
    }

    public List<BeerDTO> listAll() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws BeerNotFoundException {
        verifyIfExists(id);
        beerRepository.deleteById(id);
    }

    public BeerDTO findById(Long id) throws BeerNotFoundException {
        Beer beerFound = verifyIfExists(id);
        return beerMapper.toDTO(beerFound);
    }

    public MessageResponseDTO updateById(Long id, BeerDTO beerDTO) throws BeerNotFoundException {
        verifyIfExists(id);
        Beer beerToUpdate = beerMapper.toModel(beerDTO);

        Beer updatedBeer = beerRepository.save(beerToUpdate);

        return getMessageResponseDTO("Updated Beer with ID: " + updatedBeer.getId());
    }

    private MessageResponseDTO getMessageResponseDTO(String message) {
        return MessageResponseDTO.builder()
                .message(message)
                .build();
    }


    private void verifyIfIsAlreadyRegistered(String name) throws BeerAlreadyRegisteredException {
        Optional<Beer> optSavedBeer = beerRepository.findByName(name);
        if (optSavedBeer.isPresent()) {
            throw new BeerAlreadyRegisteredException(name);
        }

    }

    private Beer verifyIfExists(Long id) throws BeerNotFoundException {
        return beerRepository.findById(id).orElseThrow(() -> new BeerNotFoundException(id));
    }


    public BeerDTO increment(Long id, Integer quantityToIncrement) throws BeerStockExceededException, BeerNotFoundException {
        Beer beerToIncrementStock = verifyIfExists(id);

        if(quantityToIncrement > beerToIncrementStock.getMax()){
            throw new BeerStockExceededException(id, quantityToIncrement);
        }

        Integer amountLeftToMax = beerToIncrementStock.getMax() - beerToIncrementStock.getQuantity();

        if(quantityToIncrement > amountLeftToMax){
            throw new BeerStockExceededException(id, quantityToIncrement);
        }

        Integer quantityAfterIncrement = beerToIncrementStock.getQuantity() + quantityToIncrement;

        beerToIncrementStock.setQuantity(quantityAfterIncrement);
        Beer incrementedBeerStock = beerRepository.save(beerToIncrementStock);
        return beerMapper.toDTO(incrementedBeerStock);

    }

    public BeerDTO decrement(Long id, Integer quantityToDecrement) throws BeerNotFoundException, BeerNegativeStockException {
        Beer beerToDecrementStock = verifyIfExists(id);

        if(quantityToDecrement > beerToDecrementStock.getQuantity()) {
            throw new BeerNegativeStockException(id, quantityToDecrement);
        }

        Integer quantityAfterDecrement = beerToDecrementStock.getQuantity() - quantityToDecrement;

        beerToDecrementStock.setQuantity(quantityAfterDecrement);
        Beer incrementedBeerStock = beerRepository.save(beerToDecrementStock);
        return beerMapper.toDTO(incrementedBeerStock);

    }
}
