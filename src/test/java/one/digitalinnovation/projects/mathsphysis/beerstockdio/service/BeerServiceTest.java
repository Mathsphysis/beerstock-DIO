package one.digitalinnovation.projects.mathsphysis.beerstockdio.service;

import one.digitalinnovation.projects.mathsphysis.beerstockdio.builder.BeerDTOBuilder;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.dto.request.BeerDTO;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.dto.response.MessageResponseDTO;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.entity.Beer;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerNegativeStockException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerNotFoundException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerStockExceededException;
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
import org.mockito.internal.matchers.GreaterOrEqual;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {
    private static final Long INVALID_BEER_ID = 2L;
    private static final Long VALID_BEER_ID = 1L;
    private static final String UPDATE_MESSAGE = "Updated Beer with ID: ";

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

        assertThat(createdBeerDTO, is(expectedBeerDTO));
    }

    @Test
    void whenInformedBeerNameAlreadyExistsThenItShouldThrowAnException() {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer beer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.of(beer));

        assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.createBeer(expectedBeerDTO));
    }

    @Test
    void whenInformedBeerNameExistsThenItShouldReturnRegisteredBeerDTO() throws BeerNotFoundException {
        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedFoundBeer = beerMapper.toModel(expectedFoundBeerDTO);

        when(beerRepository.findByName(expectedFoundBeerDTO.getName())).thenReturn(Optional.of(expectedFoundBeer));

        BeerDTO returnedBeerDTO = beerService.findByName(expectedFoundBeerDTO.getName());

        assertThat(returnedBeerDTO, is(expectedFoundBeerDTO));
    }

    @Test
    void whenInformedNotExistingBeerNameThenItShouldThrowAnException() {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.empty());

        assertThrows(BeerNotFoundException.class, () -> beerService.findByName(expectedBeerDTO.getName()));
    }

    @Test
    void whenInformedBeerIdAlreadyExistsThenItShouldReturnRegisteredBeerDTO() throws BeerNotFoundException {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Optional<Beer> optBeer = Optional.of(beerMapper.toModel(expectedBeerDTO));

        when(beerRepository.findById(VALID_BEER_ID)).thenReturn(optBeer);

        BeerDTO returnedBeerDTO = beerService.findById(VALID_BEER_ID);

        assertThat(returnedBeerDTO, is(expectedBeerDTO));
    }

    @Test
    void whenBeerNotExistingIdInformedThenItShouldThrowAnException() {

        when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

        assertThrows(BeerNotFoundException.class, () -> beerService.findById(INVALID_BEER_ID));
    }

    @Test
    void whenBeerAndIdInformedThenReturnUpdateMessage() throws BeerNotFoundException {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);
        MessageResponseDTO expectedMessage = MessageResponseDTO.builder().message(UPDATE_MESSAGE + expectedBeer.getId()).build();

        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
        when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);

        MessageResponseDTO createdMessage = beerService.updateById(expectedBeerDTO.getId(), expectedBeerDTO);

        assertThat(createdMessage, is(expectedMessage));
    }

    @Test
    void whenBeerAndNotExistingIdInformedThenItShouldThrowAnException() {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

        assertThrows(BeerNotFoundException.class, () -> beerService.updateById(INVALID_BEER_ID, expectedBeerDTO));
    }

    @Test
    void whenListAllIsCalledThenReturnListOfRegisteredBeerDTO() {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);
        List<BeerDTO> expectedBeerDTOList = Collections.singletonList(expectedBeerDTO);

        when(beerRepository.findAll()).thenReturn(Collections.singletonList(expectedBeer));

        List<BeerDTO> returnedBeerDTOList = beerService.listAll();

        assertThat(returnedBeerDTOList, is(expectedBeerDTOList));
    }

    @Test
    void whenListAllIsCalledThenReturnAnEmptyList() {

        when(beerRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<BeerDTO> returnedBeerDTOList = beerService.listAll();

        assertThat(returnedBeerDTOList, is(empty()));
    }

    @Test
    void whenBeerValidIdIsInformedThenTheBeerShouldBeDeleted() throws BeerNotFoundException {
        BeerDTO expectedDeletedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedDeletedBeer = beerMapper.toModel(expectedDeletedBeerDTO);

        when(beerRepository.findById(VALID_BEER_ID)).thenReturn(Optional.of(expectedDeletedBeer));

        doNothing().when(beerRepository).deleteById(VALID_BEER_ID);

        beerService.deleteById(VALID_BEER_ID);

        verify(beerRepository, times(1)).findById(VALID_BEER_ID);
        verify(beerRepository, times(1)).deleteById(VALID_BEER_ID);
    }

    @Test
    void whenBeerInvalidIdIsInformedThenItShouldThrowAnException() {

        when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

        assertThrows(BeerNotFoundException.class, () -> beerService.deleteById(INVALID_BEER_ID));

    }

    @Test
    void whenIncrementIsCalledThenIncrementBeerStock() throws BeerNotFoundException, BeerStockExceededException {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
        when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);

        Integer quantityToIncrement = 10;
        Integer expectedQuantityAfterIncrement = expectedBeerDTO.getQuantity() + quantityToIncrement;
        BeerDTO incrementedBeerDTO = beerService.increment(expectedBeerDTO.getId(), quantityToIncrement);

        assertThat(expectedQuantityAfterIncrement, is(incrementedBeerDTO.getQuantity()));
        assertThat(expectedQuantityAfterIncrement, is(lessThanOrEqualTo(expectedBeerDTO.getMax())));
    }

    @Test
    void whenIncrementIsGreaterThanAmountLeftIsCalledThenItShouldThrowAnException() {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

        Integer quantityToIncrement = 41;

        assertThrows(BeerStockExceededException.class, () -> beerService.increment(expectedBeerDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementIsGreaterThanMaxAmountIsCalledThenItShouldThrowAnException() {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

        Integer quantityToIncrement = 51;

        assertThrows(BeerStockExceededException.class, () -> beerService.increment(expectedBeerDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementIsCalledWithBeerInvalidIdThenItShouldThrowAnException() {

        when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

        Integer quantityToIncrement = 10;

        assertThrows(BeerNotFoundException.class, () -> beerService.increment(INVALID_BEER_ID, quantityToIncrement));
    }

    @Test
    void whenDecrementIsCalledThenDecrementBeerStock() throws BeerNotFoundException, BeerNegativeStockException {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
        when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);

        Integer quantityToDecrement = 6;
        Integer expectedQuantityAfterDecrement = expectedBeerDTO.getQuantity() - quantityToDecrement;
        BeerDTO decrementedBeerDTO = beerService.decrement(expectedBeerDTO.getId(), quantityToDecrement);

        assertThat(expectedQuantityAfterDecrement, is(decrementedBeerDTO.getQuantity()));
        assertThat(expectedQuantityAfterDecrement, is(greaterThanOrEqualTo(0)));
    }

    @Test
    void whenDecrementIsGreaterThanCurrentAmountIsCalledThenItShouldThrowAnException() {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

        Integer quantityToDecrement = 41;

        assertThrows(BeerNegativeStockException.class, () -> beerService.decrement(expectedBeerDTO.getId(), quantityToDecrement));
    }

    @Test
    void whenDecrementIsCalledWithBeerInvalidIdThenItShouldThrowAnException() {

        when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

        Integer quantityToDecrement = 10;

        assertThrows(BeerNotFoundException.class, () -> beerService.decrement(INVALID_BEER_ID, quantityToDecrement));
    }
}
