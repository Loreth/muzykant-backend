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
import pl.kamilprzenioslo.muzykant.dtos.MusicianWantedAd;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianWantedAdEntity;
import pl.kamilprzenioslo.muzykant.service.MusicianWantedAdService;
import pl.kamilprzenioslo.muzykant.specifications.AdWithLookingPreferredGenresSpecification;
import pl.kamilprzenioslo.muzykant.specifications.AdWithLookingPreferredInstrumentsSpecification;
import pl.kamilprzenioslo.muzykant.specifications.AdWithPreferredGenresSpecification;
import pl.kamilprzenioslo.muzykant.specifications.AdWithPreferredInstrumentsSpecification;
import pl.kamilprzenioslo.muzykant.specifications.AdWithVoivodeshipsSpecification;
import pl.kamilprzenioslo.muzykant.specifications.MusicianWantedAdSpecification;

@RestController
@RequestMapping(RestMappings.MUSICIAN_WANTED_AD)
public class MusicianWantedAdController extends BaseRestController<MusicianWantedAd, Integer> {

  private final MusicianWantedAdService service;

  public MusicianWantedAdController(MusicianWantedAdService service) {
    super(service);
    this.service = service;
  }

  @GetMapping(RestMappings.SEARCH)
  public Page<MusicianWantedAd> getAllWithGivenParameters(
      MusicianWantedAdSpecification specification,
      @RequestParam(required = false) List<Integer> voivodeshipIds,
      @RequestParam(required = false) List<Integer> preferredGenreIds,
      @RequestParam(required = false) List<Integer> preferredInstrumentIds,
      @RequestParam(required = false) List<Integer> lookingPreferredGenreIds,
      @RequestParam(required = false) List<Integer> lookingPreferredInstrumentIds,
      Pageable pageable) {

    return service.findAll(
        Stream.of(
                specification,
                new AdWithVoivodeshipsSpecification<MusicianWantedAdEntity>(voivodeshipIds),
                new AdWithPreferredGenresSpecification<MusicianWantedAdEntity>(preferredGenreIds),
                new AdWithPreferredInstrumentsSpecification<MusicianWantedAdEntity>(
                    preferredInstrumentIds),
                new AdWithLookingPreferredGenresSpecification<MusicianWantedAdEntity>(
                    lookingPreferredGenreIds),
                new AdWithLookingPreferredInstrumentsSpecification<MusicianWantedAdEntity>(
                    lookingPreferredInstrumentIds))
            .collect(Collectors.toList()),
        pageable);
  }
}
