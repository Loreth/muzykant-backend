package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.RegularUser;
import pl.kamilprzenioslo.muzykant.persistance.entities.RegularUserEntity;
import pl.kamilprzenioslo.muzykant.service.RegularUserService;
import pl.kamilprzenioslo.muzykant.specifications.RegularUserSpecification;

@RestController
@RequestMapping(RestMappings.REGULAR_USER)
public class RegularUserController
    extends SpecificationRestController<
        RegularUser, RegularUserEntity, Integer, RegularUserSpecification, RegularUserService> {

  public RegularUserController(RegularUserService service) {
    super(service);
  }
}
