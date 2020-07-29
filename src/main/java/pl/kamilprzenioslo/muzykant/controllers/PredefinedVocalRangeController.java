package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.PredefinedVocalRange;
import pl.kamilprzenioslo.muzykant.service.PredefinedVocalRangeService;

@RestController
@RequestMapping(RestMappings.PREDEFINED_VOCAL_RANGE)
public class PredefinedVocalRangeController extends GetController<PredefinedVocalRange, Integer> {

  public PredefinedVocalRangeController(PredefinedVocalRangeService service) {
    super(service);
  }
}
