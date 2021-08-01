package one.digitalinnovation.projects.mathsphysis.beerstockdio.service;

import one.digitalinnovation.projects.mathsphysis.beerstockdio.builder.BeerDTOBuilder;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.dto.request.BeerDTO;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.entity.Beer;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerNotFoundException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.mapper.BeerMapper;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.repository.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {
    private static final Long INVALID_BEE_ID = 1L;

    @Mock
    private BeerRepository beerRepository;

    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks
    private BeerService beerService;

    @Test
    void whenBeerInformedThenItShouldBeCreated() throws BeerAlreadyRegisteredException {
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.toModel(beerDTO);

        Mockito.when(beerRepository.findByName(beerDTO.getName())).thenReturn(Optional.empty());
        Mockito.when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);

        BeerDTO createdBeerDTO = beerService.createBeer(beerDTO);
        
        Assertions.assertEquals(beerDTO, createdBeerDTO);
    }

    @Test
    void whenInformedBeerAlreadyExistsThenItShouldThrowAnException() throws BeerAlreadyRegisteredException {
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer beer = beerMapper.toModel(beerDTO);

        Mockito.when(beerRepository.findByName(beerDTO.getName())).thenReturn(Optional.of(beer));

        Assertions.assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.createBeer(beerDTO));
    }

    @Test
    void whenBeerIdInformedThenItShouldReturnExistingBeerDTO() throws BeerNotFoundException {
        Long id = 1L;
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Optional<Beer> optBeer = Optional.of(beerMapper.toModel(beerDTO));

        Mockito.when(beerRepository.findById(id)).thenReturn(optBeer);

        BeerDTO returnedBeerDTO = beerService.findById(id);

        Assertions.assertEquals(beerDTO, returnedBeerDTO);
    }

    @Test
    void whenBeerNotExistingIdInformedThenItShouldThrowAnException() throws BeerNotFoundException {
        Long id = 2L;

        Mockito.when(beerRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(BeerNotFoundException.class, () -> beerService.findById(id));
    }
}
