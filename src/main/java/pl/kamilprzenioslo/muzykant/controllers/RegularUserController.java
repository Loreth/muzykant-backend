package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.RegularUser;
import pl.kamilprzenioslo.muzykant.service.RegularUserService;

@RestController
@RequestMapping(RestMappings.REGULAR_USER)
public class RegularUserController extends BaseRestController<RegularUser, Integer> {

  public RegularUserController(RegularUserService service) {
    super(service);
  }
}
