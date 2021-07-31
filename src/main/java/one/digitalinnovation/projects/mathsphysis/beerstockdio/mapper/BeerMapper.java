package one.digitalinnovation.projects.mathsphysis.beerstock.mapper;

import one.digitalinnovation.projects.mathsphysis.beerstock.dto.request.BeerDTO;
import one.digitalinnovation.projects.mathsphysis.beerstock.entity.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeerMapper {

        BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);

        Beer toModel(BeerDTO beerDTO);

        BeerDTO toDTO(Beer beer);

}
