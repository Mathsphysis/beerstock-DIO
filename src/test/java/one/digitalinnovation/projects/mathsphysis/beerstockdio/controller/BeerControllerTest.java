package one.digitalinnovation.projects.mathsphysis.beerstockdio.controller;

import one.digitalinnovation.projects.mathsphysis.beerstockdio.builder.BeerDTOBuilder;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.dto.request.BeerDTO;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.dto.response.MessageResponseDTO;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.exception.BeerNotFoundException;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.service.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static one.digitalinnovation.projects.mathsphysis.beerstockdio.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BeerControllerTest {

    private static final String BEER_API_URL_PATH = "/api/v1/beers";
    private static final Long VALID_BEER_ID = 1l;
    private static final Long INVALID_BEER_ID = 2l;
    private static final String BEER_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String BEER_API_SUBPATH_DECREMENT_URL = "/decrement";
    private static final String NOT_EXISTING_BEER_NAME = "Not Existing";

    private MockMvc mockMvc;

    @Mock
    private BeerService beerService;

    @InjectMocks
    private BeerController beerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(beerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenBeerIsCreated() throws Exception {
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerService.createBeer(beerDTO)).thenReturn(beerDTO);

        mockMvc.perform(post(BEER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beerDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name", is(beerDTO.getName())))
                    .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
                    .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenRespondsWithBadRequestStatus() throws Exception {
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        beerDTO.setBrand(null);

        mockMvc.perform(post(BEER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPUTIsCalledThenBeerIsUpdated() throws Exception {
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        MessageResponseDTO expectedMessageResponseDTO = MessageResponseDTO.builder().message("Updated Beer with ID: " + beerDTO.getId()).build();

        when(beerService.updateById(VALID_BEER_ID, beerDTO)).thenReturn(expectedMessageResponseDTO);

        mockMvc.perform(put(BEER_API_URL_PATH + "/" + VALID_BEER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponseDTO.getMessage())));
    }

    @Test
    void whenPUTIsCalledWithoutRequiredFieldThenRespondsWithBadRequestStatus() throws Exception {
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        beerDTO.setBrand(null);

        mockMvc.perform(put(BEER_API_URL_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPUTIsCalledWithInvalidIdThenRespondsWithNotFoundStatus() throws Exception {
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerService.updateById(INVALID_BEER_ID, beerDTO)).thenThrow(BeerNotFoundException.class);

        mockMvc.perform(put(BEER_API_URL_PATH + "/" + INVALID_BEER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beerDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETIsCalledWithValidNameThenRespondsWithOKStatus() throws Exception {
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerService.findByName(beerDTO.getName())).thenReturn(beerDTO);

        mockMvc.perform(get(BEER_API_URL_PATH + "/" + beerDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(beerDTO.getName())))
                .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())));
    }

    @Test
    void whenGETIsCalledWithInvalidNameThenRespondsWithNotFoundStatus() throws Exception {

        when(beerService.findByName(NOT_EXISTING_BEER_NAME)).thenThrow(BeerNotFoundException.class);

        mockMvc.perform(get(BEER_API_URL_PATH + "/" + NOT_EXISTING_BEER_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
