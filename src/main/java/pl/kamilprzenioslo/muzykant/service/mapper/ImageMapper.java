package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kamilprzenioslo.muzykant.dtos.Image;
import pl.kamilprzenioslo.muzykant.persistance.entities.ImageEntity;

@Mapper(uses = EntityFactory.class)
public interface ImageMapper extends BaseMapper<Image, ImageEntity> {

  @Mapping(target = "userId", source = "user.id")
  @Override
  Image mapToDto(ImageEntity entity);

  @Mapping(target = "user", source = "userId")
  @Override
  ImageEntity mapToEntity(Image dto);
}
