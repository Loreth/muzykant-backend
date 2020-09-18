package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kamilprzenioslo.muzykant.dtos.SocialMediaLinks;
import pl.kamilprzenioslo.muzykant.persistance.entities.SocialMediaLinksEntity;

@Mapper(uses = EntityReferenceFactory.class)
public interface SocialMediaLinksMapper
    extends BaseMapper<SocialMediaLinks, SocialMediaLinksEntity> {

  @Mapping(target = "userId", source = "user.id")
  @Override
  SocialMediaLinks mapToDto(SocialMediaLinksEntity entity);

  @Mapping(target = "user", source = "userId")
  @Override
  SocialMediaLinksEntity mapToEntity(SocialMediaLinks dto);
}
