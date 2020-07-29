package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.Image;
import pl.kamilprzenioslo.muzykant.persistance.entities.ImageEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.ImageRepository;
import pl.kamilprzenioslo.muzykant.service.ImageService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class ImageServiceImpl
    extends BaseSpecificationCrudService<Image, ImageEntity, Integer, ImageRepository>
    implements ImageService {

  public ImageServiceImpl(ImageRepository repository, BaseMapper<Image, ImageEntity> mapper) {
    super(repository, mapper);
  }
}
