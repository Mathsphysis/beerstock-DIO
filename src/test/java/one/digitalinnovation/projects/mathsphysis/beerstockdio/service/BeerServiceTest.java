package one.digitalinnovation.projects.mathsphysis.beerstockdio.service;

import one.digitalinnovation.projects.mathsphysis.beerstockdio.builder.BeerDTOBuilder;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.dto.request.BeerDTO;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.entity.Beer;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerNotFoundException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.mapper.BeerMapper;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.repository.BeerRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.empty());
        when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);

        BeerDTO createdBeerDTO = beerService.createBeer(expectedBeerDTO);

        assertThat(expectedBeerDTO, is(createdBeerDTO));
    }

    @Test
    void whenInformedBeerAlreadyExistsThenItShouldThrowAnException() throws BeerAlreadyRegisteredException {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer beer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.of(beer));
        
        assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.createBeer(expectedBeerDTO));
    }

    @Test
    void whenBeerIdInformedThenItShouldReturnExistingBeerDTO() throws BeerNotFoundException {
        Long id = 1L;
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Optional<Beer> optBeer = Optional.of(beerMapper.toModel(expectedBeerDTO));

        when(beerRepository.findById(id)).thenReturn(optBeer);

        BeerDTO returnedBeerDTO = beerService.findById(id);

        assertThat(expectedBeerDTO, is(returnedBeerDTO));
    }

    @Test
    void whenBeerNotExistingIdInformedThenItShouldThrowAnException() throws BeerNotFoundException {
        Long id = 2L;

        when(beerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BeerNotFoundException.class, () -> beerService.findById(id));
    }
}
