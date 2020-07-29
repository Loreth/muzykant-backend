package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.Band;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandEntity;
import pl.kamilprzenioslo.muzykant.service.BandService;
import pl.kamilprzenioslo.muzykant.specifications.BandSpecification;

@RestController
@RequestMapping(RestMappings.BAND)
public class BandController
    extends SpecificationRestController<Band, BandEntity, Integer, BandSpecification, BandService> {

  public BandController(BandService service) {
    super(service);
  }
}
