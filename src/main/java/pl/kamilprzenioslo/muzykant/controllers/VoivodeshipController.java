package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.Voivodeship;
import pl.kamilprzenioslo.muzykant.service.VoivodeshipService;

@RestController
@RequestMapping(RestMappings.VOIVODESHIP)
public class VoivodeshipController extends GetController<Voivodeship, Integer> {

  public VoivodeshipController(VoivodeshipService service) {
    super(service);
  }
}
