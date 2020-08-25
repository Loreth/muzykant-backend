package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.RegularUser;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.persistance.entities.RegularUserEntity;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;
import pl.kamilprzenioslo.muzykant.service.RegularUserService;
import pl.kamilprzenioslo.muzykant.specifications.RegularUserSpecification;

@RestController
@RequestMapping(RestMappings.REGULAR_USER)
public class RegularUserController
    extends SpecificationRestController<
        RegularUser, RegularUserEntity, Integer, RegularUserSpecification, RegularUserService> {

  private final RegularUserService service;
  private final CredentialsService credentialsService;

  public RegularUserController(RegularUserService service, CredentialsService credentialsService) {
    super(service);
    this.service = service;
    this.credentialsService = credentialsService;
  }

  @PostMapping(RestMappings.SIGN_UP)
  public void signUp(@RequestBody SignUpRequest<RegularUser> signUpRequest) {
    RegularUser savedRegularUser = service.save(signUpRequest.getUser());
    credentialsService.signUp(
        signUpRequest, UserAuthority.ROLE_REGULAR_USER, savedRegularUser.getId());
  }
}
