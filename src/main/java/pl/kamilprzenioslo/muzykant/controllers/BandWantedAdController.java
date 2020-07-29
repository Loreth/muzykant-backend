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
import pl.kamilprzenioslo.muzykant.dtos.BandWantedAd;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandWantedAdEntity;
import pl.kamilprzenioslo.muzykant.service.BandWantedAdService;
import pl.kamilprzenioslo.muzykant.specifications.AdWithPreferredGenresSpecification;
import pl.kamilprzenioslo.muzykant.specifications.AdWithPreferredInstrumentsSpecification;
import pl.kamilprzenioslo.muzykant.specifications.BandWantedAdSpecification;

@RestController
@RequestMapping(RestMappings.BAND_WANTED_AD)
public class BandWantedAdController extends BaseRestController<BandWantedAd, Integer> {

  private final BandWantedAdService service;

  public BandWantedAdController(BandWantedAdService service) {
    super(service);
    this.service = service;
  }

  @GetMapping(RestMappings.SEARCH)
  public Page<BandWantedAd> getAllWithGivenParameters(
      BandWantedAdSpecification specification,
      @RequestParam(required = false) List<Integer> preferredGenreIds,
      @RequestParam(required = false) List<Integer> preferredInstrumentIds,
      Pageable pageable) {

    return service.findAll(
        Stream.of(
                specification,
                new AdWithPreferredGenresSpecification<BandWantedAdEntity>(preferredGenreIds),
                new AdWithPreferredInstrumentsSpecification<BandWantedAdEntity>(
                    preferredInstrumentIds))
            .collect(Collectors.toList()),
        pageable);
  }
}
