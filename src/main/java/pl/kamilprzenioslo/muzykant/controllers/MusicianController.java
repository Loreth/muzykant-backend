package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.Musician;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianEntity;
import pl.kamilprzenioslo.muzykant.service.MusicianService;
import pl.kamilprzenioslo.muzykant.specifications.MusicianSpecification;

@RestController
@RequestMapping(RestMappings.MUSICIAN)
public class MusicianController
    extends SpecificationRestController<
        Musician, MusicianEntity, Integer, MusicianSpecification, MusicianService> {

  public MusicianController(MusicianService service) {
    super(service);
  }
}
