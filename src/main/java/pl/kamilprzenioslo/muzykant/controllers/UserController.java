package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.controllers.mappings.RestMappings;
import pl.kamilprzenioslo.muzykant.dtos.User;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;
import pl.kamilprzenioslo.muzykant.service.UserService;
import pl.kamilprzenioslo.muzykant.specifications.UserSpecification;

@RestController
@RequestMapping(RestMappings.USER)
public class UserController
    extends SpecificationRestController<
        User, UserEntity, Integer, UserSpecification<UserEntity>, UserService> {

  public UserController(UserService service) {
    super(service);
  }
}
