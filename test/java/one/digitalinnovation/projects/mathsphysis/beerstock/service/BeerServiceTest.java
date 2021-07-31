package one.digitalinnovation.projects.mathsphysis.beerstock.service;

import one.digitalinnovation.projects.mathsphysis.beerstock.builder.BeerDTOBuilder;
import one.digitalinnovation.projects.mathsphysis.beerstock.dto.request.BeerDTO;
import one.digitalinnovation.projects.mathsphysis.beerstock.entity.Beer;
import one.digitalinnovation.projects.mathsphysis.beerstock.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.projects.mathsphysis.beerstock.mapper.BeerMapper;
import one.digitalinnovation.projects.mathsphysis.beerstock.repository.BeerRepository;
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

        Assertions.assertEquals(beerDTO.getId(), createdBeerDTO.getId());
    }
}
