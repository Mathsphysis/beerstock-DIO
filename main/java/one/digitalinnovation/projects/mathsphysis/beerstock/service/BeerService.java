package one.digitalinnovation.projects.mathsphysis.beerstock.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.projects.mathsphysis.beerstock.dto.request.BeerDTO;
import one.digitalinnovation.projects.mathsphysis.beerstock.dto.response.MessageResponseDTO;
import one.digitalinnovation.projects.mathsphysis.beerstock.entity.Beer;
import one.digitalinnovation.projects.mathsphysis.beerstock.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.projects.mathsphysis.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.projects.mathsphysis.beerstock.mapper.BeerMapper;
import one.digitalinnovation.projects.mathsphysis.beerstock.repository.BeerRepository;
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
        if( optSavedBeer.isPresent()){
            throw new BeerAlreadyRegisteredException(name);
        }

    }

    private Beer verifyIfExists(Long id) throws BeerNotFoundException {
        return beerRepository.findById(id).orElseThrow(() -> new BeerNotFoundException(id));
    }
}
