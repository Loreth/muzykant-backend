package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.controllers.mappings.RestMappings;
import pl.kamilprzenioslo.muzykant.dtos.Genre;
import pl.kamilprzenioslo.muzykant.persistance.entities.GenreEntity;
import pl.kamilprzenioslo.muzykant.service.GenreService;
import pl.kamilprzenioslo.muzykant.specifications.GenreSpecification;

@RestController
@RequestMapping(RestMappings.GENRE)
public class GenreController
    extends SpecificationRestController<
        Genre, GenreEntity, Integer, GenreSpecification, GenreService> {

  public GenreController(GenreService service) {
    super(service);
  }
}
