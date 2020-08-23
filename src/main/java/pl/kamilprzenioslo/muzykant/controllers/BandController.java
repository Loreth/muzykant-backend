package pl.kamilprzenioslo.muzykant.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.Band;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandEntity;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;
import pl.kamilprzenioslo.muzykant.service.BandService;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;
import pl.kamilprzenioslo.muzykant.specifications.BandSpecification;
import pl.kamilprzenioslo.muzykant.specifications.UserWithGenresSpecification;
import pl.kamilprzenioslo.muzykant.specifications.UserWithInstrumentsSpecification;

@RestController
@RequestMapping(RestMappings.BAND)
public class BandController extends BaseRestController<Band, Integer> {

  private final BandService service;
  private final CredentialsService credentialsService;

  public BandController(BandService service, CredentialsService credentialsService) {
    super(service);
    this.service = service;
    this.credentialsService = credentialsService;
  }

  @GetMapping(RestMappings.SEARCH)
  public Page<Band> getAllWithGivenParameters(
      BandSpecification specification,
      @RequestParam(required = false) List<Integer> genreIds,
      @RequestParam(required = false) List<Integer> instrumentIds,
      Pageable pageable) {

    return service.findAll(
        Stream.of(
                specification,
                new UserWithGenresSpecification<BandEntity>(genreIds),
                new UserWithInstrumentsSpecification<BandEntity>(instrumentIds))
            .collect(Collectors.toList()),
        pageable);
  }

  @PostMapping(RestMappings.SIGN_UP)
  public void signUp(SignUpRequest<Band> signUpRequest) {
    Band savedBand = service.save(signUpRequest.getUser());
    credentialsService.signUp(signUpRequest, UserAuthority.ROLE_BAND, savedBand.getId());
  }
}
