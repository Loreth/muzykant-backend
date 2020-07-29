package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.Image;
import pl.kamilprzenioslo.muzykant.persistance.entities.ImageEntity;
import pl.kamilprzenioslo.muzykant.service.ImageService;
import pl.kamilprzenioslo.muzykant.specifications.ImageSpecification;

@RestController
@RequestMapping(RestMappings.IMAGE)
public class ImageController
    extends SpecificationRestController<
        Image, ImageEntity, Integer, ImageSpecification, ImageService> {

  public ImageController(ImageService service) {
    super(service);
  }
}
