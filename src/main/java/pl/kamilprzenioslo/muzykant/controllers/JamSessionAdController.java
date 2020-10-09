package pl.kamilprzenioslo.muzykant.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.controllers.mappings.RestMappings;
import pl.kamilprzenioslo.muzykant.dtos.JamSessionAd;
import pl.kamilprzenioslo.muzykant.persistance.entities.JamSessionAdEntity;
import pl.kamilprzenioslo.muzykant.service.JamSessionAdService;
import pl.kamilprzenioslo.muzykant.specifications.AdWithPreferredGenresSpecification;
import pl.kamilprzenioslo.muzykant.specifications.AdWithPreferredInstrumentsSpecification;
import pl.kamilprzenioslo.muzykant.specifications.AdWithVoivodeshipsSpecification;
import pl.kamilprzenioslo.muzykant.specifications.JamSessionAdSpecification;

@RestController
@RequestMapping(RestMappings.JAM_SESSION_AD)
public class JamSessionAdController extends BaseRestController<JamSessionAd, Integer> {

  private final JamSessionAdService service;

  public JamSessionAdController(JamSessionAdService service) {
    super(service);
    this.service = service;
  }

  @GetMapping(RestMappings.SEARCH)
  public Page<JamSessionAd> getAllWithGivenParameters(
      JamSessionAdSpecification specification,
      @RequestParam(required = false) List<Integer> voivodeshipIds,
      @RequestParam(required = false) List<Integer> preferredGenreIds,
      @RequestParam(required = false) List<Integer> preferredInstrumentIds,
      Pageable pageable) {

    return service.findAll(
        Stream.of(
                specification,
                new AdWithVoivodeshipsSpecification<JamSessionAdEntity>(voivodeshipIds),
                new AdWithPreferredGenresSpecification<JamSessionAdEntity>(preferredGenreIds),
                new AdWithPreferredInstrumentsSpecification<JamSessionAdEntity>(
                    preferredInstrumentIds))
            .collect(Collectors.toList()),
        pageable);
  }
}
