package pl.kamilprzenioslo.muzykant.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.Band;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandEntity;
import pl.kamilprzenioslo.muzykant.security.UserAuthority;
import pl.kamilprzenioslo.muzykant.service.BandService;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;
import pl.kamilprzenioslo.muzykant.specifications.BandSpecification;
import pl.kamilprzenioslo.muzykant.specifications.UserWithGenresSpecification;
import pl.kamilprzenioslo.muzykant.specifications.UserWithInstrumentsSpecification;
import pl.kamilprzenioslo.muzykant.validation.OnPost;

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

  @Override
  @Validated(OnPost.class)
  @PostMapping
  public ResponseEntity<Band> create(@Valid @RequestBody Band dto, HttpServletRequest request) {
    ResponseEntity<Band> responseEntity = super.create(dto, request);
    credentialsService.assignUserProfileToCurrentlyAuthenticatedUser(
        UserAuthority.ROLE_BAND, responseEntity.getBody().getId());

    return responseEntity;
  }
}
