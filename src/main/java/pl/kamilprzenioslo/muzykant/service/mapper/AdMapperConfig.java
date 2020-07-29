package pl.kamilprzenioslo.muzykant.service.mapper;

import static org.mapstruct.MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import pl.kamilprzenioslo.muzykant.dtos.Ad;
import pl.kamilprzenioslo.muzykant.persistance.entities.AdEntity;

@MapperConfig(
    uses = {EntityFactory.class, GenreMapper.class},
    mappingInheritanceStrategy = AUTO_INHERIT_ALL_FROM_CONFIG)
public interface AdMapperConfig extends BaseMapper<Ad, AdEntity> {

  @Mapping(target = "userId", source = "user.id")
  @Override
  Ad mapToDto(AdEntity entity);

  @Mapping(target = "user", source = "userId")
  @Override
  AdEntity mapToEntity(Ad dto);
}
