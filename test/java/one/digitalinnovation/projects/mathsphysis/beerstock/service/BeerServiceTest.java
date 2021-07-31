package one.digitalinnovation.projects.mathsphysis.beerstock.service;

import one.digitalinnovation.projects.mathsphysis.beerstock.mapper.BeerMapper;
import one.digitalinnovation.projects.mathsphysis.beerstock.repository.BeerRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {
    private static final Long INVALID_BEE_ID = 1L;

    @Mock
    private BeerRepository beerRepository;

    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks
    private BeerService beerService;

    void whenBeerInformedThenItShouldBeCreated() {

    }
}
