package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.controllers.mappings.RestMappings;
import pl.kamilprzenioslo.muzykant.dtos.Instrument;
import pl.kamilprzenioslo.muzykant.persistance.entities.InstrumentEntity;
import pl.kamilprzenioslo.muzykant.service.InstrumentService;
import pl.kamilprzenioslo.muzykant.specifications.InstrumentSpecification;

@RestController
@RequestMapping(RestMappings.INSTRUMENT)
public class InstrumentController
    extends SpecificationRestController<
        Instrument, InstrumentEntity, Integer, InstrumentSpecification, InstrumentService> {

  public InstrumentController(InstrumentService service) {
    super(service);
  }
}
