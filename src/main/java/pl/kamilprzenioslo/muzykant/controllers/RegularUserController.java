package pl.kamilprzenioslo.muzykant.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.RegularUser;
import pl.kamilprzenioslo.muzykant.persistance.entities.RegularUserEntity;
import pl.kamilprzenioslo.muzykant.security.UserAuthority;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;
import pl.kamilprzenioslo.muzykant.service.RegularUserService;
import pl.kamilprzenioslo.muzykant.specifications.RegularUserSpecification;
import pl.kamilprzenioslo.muzykant.validation.OnPost;

@RestController
@RequestMapping(RestMappings.REGULAR_USER)
public class RegularUserController
    extends SpecificationRestController<
    RegularUser, RegularUserEntity, Integer, RegularUserSpecification, RegularUserService> {

  private final CredentialsService credentialsService;

  public RegularUserController(RegularUserService service, CredentialsService credentialsService) {
    super(service);
    this.credentialsService = credentialsService;
  }

  @Override
  @Validated(OnPost.class)
  @PostMapping
  public ResponseEntity<RegularUser> create(
      @Valid @RequestBody RegularUser dto, HttpServletRequest request) {
    ResponseEntity<RegularUser> responseEntity = super.create(dto, request);
    credentialsService.assignUserProfileToCurrentlyAuthenticatedUser(
        UserAuthority.ROLE_REGULAR_USER, responseEntity.getBody().getId());

    return responseEntity;
  }
}
