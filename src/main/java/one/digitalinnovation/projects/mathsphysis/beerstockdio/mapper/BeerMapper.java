package one.digitalinnovation.projects.mathsphysis.beerstockdio.mapper;

import one.digitalinnovation.projects.mathsphysis.beerstockdio.dto.request.BeerDTO;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.entity.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeerMapper {

    BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);

    Beer toModel(BeerDTO beerDTO);

    BeerDTO toDTO(Beer beer);

}
