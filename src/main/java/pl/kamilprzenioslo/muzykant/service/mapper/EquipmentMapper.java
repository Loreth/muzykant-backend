package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kamilprzenioslo.muzykant.dtos.Equipment;
import pl.kamilprzenioslo.muzykant.persistance.entities.EquipmentEntity;

@Mapper(uses = EntityFactory.class)
public interface EquipmentMapper extends BaseMapper<Equipment, EquipmentEntity> {

  @Mapping(target = "musicianId", source = "musician.id")
  @Override
  Equipment mapToDto(EquipmentEntity entity);

  @Mapping(target = "musician", source = "musicianId")
  @Override
  EquipmentEntity mapToEntity(Equipment dto);
}
