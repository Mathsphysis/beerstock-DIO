package one.digitalinnovation.projects.mathsphysis.beerstock.controller;

import lombok.AllArgsConstructor;
import one.digitalinnovation.projects.mathsphysis.beerstock.dto.request.BeerDTO;
import one.digitalinnovation.projects.mathsphysis.beerstock.dto.response.MessageResponseDTO;
import one.digitalinnovation.projects.mathsphysis.beerstock.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.projects.mathsphysis.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.projects.mathsphysis.beerstock.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beers")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerController {

    private final BeerService beerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO createBeer(@RequestBody @Valid BeerDTO beerDTO) throws BeerAlreadyRegisteredException {
        return beerService.createBeer(beerDTO);
    }

    @GetMapping
    public List<BeerDTO> listBeers(){
        return beerService.listAll();
    }

    @GetMapping("/{name}")
    public BeerDTO findByName(@PathVariable String name) throws BeerNotFoundException {
        return beerService.findByName(name);
    }

    @GetMapping("/{id}")
    public BeerDTO findById(@PathVariable Long id) throws BeerNotFoundException {
        return beerService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateById(@PathVariable Long id, @RequestBody @Valid BeerDTO beerDTO) throws BeerNotFoundException {
        return beerService.updateById(id, beerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws BeerNotFoundException {
        beerService.deleteById(id);
    }
}
