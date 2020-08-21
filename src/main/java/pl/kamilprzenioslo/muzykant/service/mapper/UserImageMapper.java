package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kamilprzenioslo.muzykant.dtos.UserImage;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserImageEntity;

@Mapper(uses = EntityFactory.class)
public interface UserImageMapper extends BaseMapper<UserImage, UserImageEntity> {

  @Mapping(target = "userId", source = "user.id")
  @Override
  UserImage mapToDto(UserImageEntity entity);

  @Mapping(target = "user", source = "userId")
  @Override
  UserImageEntity mapToEntity(UserImage dto);
}
