package one.digitalinnovation.projects.mathsphysis.beerstockdio.builder;

import lombok.Builder;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.dto.request.BeerDTO;
import one.digitalinnovation.projects.mathsphysis.beerstockdio.enums.BeerType;

@Builder
public class BeerDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Colorado Kuya";

    @Builder.Default
    private String brand = "Cervejaria Colorado";

    @Builder.Default
    private Integer max = 20;

    @Builder.Default
    private Integer quantity = 10;

    @Builder.Default
    private BeerType type = BeerType.IPA;

    public BeerDTO toBeerDTO() {
        return new BeerDTO(id, name, brand, max, quantity, type);
    }
}
