package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.SocialMediaLinks;
import pl.kamilprzenioslo.muzykant.service.SocialMediaLinksService;

@RestController
@RequestMapping(RestMappings.SOCIAL_MEDIA_LINKS)
public class SocialMediaLinksController extends BaseRestController<SocialMediaLinks, Integer> {

  public SocialMediaLinksController(SocialMediaLinksService service) {
    super(service);
  }
}
