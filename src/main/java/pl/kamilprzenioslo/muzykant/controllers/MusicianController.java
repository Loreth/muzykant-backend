package pl.kamilprzenioslo.muzykant.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.Musician;
import pl.kamilprzenioslo.muzykant.dtos.security.VerifiedEmailSignUpRequest;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianEntity;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;
import pl.kamilprzenioslo.muzykant.service.MusicianService;
import pl.kamilprzenioslo.muzykant.specifications.MusicianSpecification;
import pl.kamilprzenioslo.muzykant.specifications.UserWithGenresSpecification;
import pl.kamilprzenioslo.muzykant.specifications.UserWithInstrumentsSpecification;

@RestController
@RequestMapping(RestMappings.MUSICIAN)
public class MusicianController extends BaseRestController<Musician, Integer> {

  private final MusicianService service;
  private final CredentialsService credentialsService;

  public MusicianController(CredentialsService credentialsService, MusicianService service) {
    super(service);
    this.credentialsService = credentialsService;
    this.service = service;
  }

  @GetMapping(RestMappings.SEARCH)
  public Page<Musician> getAllWithGivenParameters(
      MusicianSpecification specification,
      @RequestParam(required = false) List<Integer> genreIds,
      @RequestParam(required = false) List<Integer> instrumentIds,
      Pageable pageable) {

    return service.findAll(
        Stream.of(
                specification,
                new UserWithGenresSpecification<MusicianEntity>(genreIds),
                new UserWithInstrumentsSpecification<MusicianEntity>(instrumentIds))
            .collect(Collectors.toList()),
        pageable);
  }

  @PostMapping(RestMappings.SIGN_UP)
  public void signUp(@RequestBody VerifiedEmailSignUpRequest<Musician> verifiedEmailSignUpRequest) {
    Musician savedMusician = service.save(verifiedEmailSignUpRequest.getUser());
    credentialsService.createAccount(
        verifiedEmailSignUpRequest, UserAuthority.ROLE_MUSICIAN, savedMusician.getId());
  }
}
