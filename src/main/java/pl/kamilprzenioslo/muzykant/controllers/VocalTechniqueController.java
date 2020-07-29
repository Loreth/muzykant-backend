package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.VocalTechnique;
import pl.kamilprzenioslo.muzykant.service.VocalTechniqueService;

@RestController
@RequestMapping(RestMappings.VOCAL_TECHNIQUE)
public class VocalTechniqueController extends BaseRestController<VocalTechnique, Integer> {

  public VocalTechniqueController(VocalTechniqueService service) {
    super(service);
  }
}
