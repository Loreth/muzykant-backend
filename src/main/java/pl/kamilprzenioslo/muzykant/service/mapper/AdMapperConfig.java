package pl.kamilprzenioslo.muzykant.service.mapper;

import static org.mapstruct.MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import pl.kamilprzenioslo.muzykant.dtos.Ad;
import pl.kamilprzenioslo.muzykant.persistance.entities.AdEntity;

@MapperConfig(
    uses = {EntityFactory.class, GenreMapper.class, InstrumentMapper.class},
    mappingInheritanceStrategy = AUTO_INHERIT_FROM_CONFIG)
public interface AdMapperConfig extends BaseMapper<Ad, AdEntity> {

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "userType", source = "user.userType")
  @Mapping(target = "userDisplayName", source = "user.displayName")
  @Mapping(target = "userGenres", source = "user.genres")
  @Override
  Ad mapToDto(AdEntity entity);

  @Mapping(target = "user", source = "userId")
  @Override
  AdEntity mapToEntity(Ad dto);
}
