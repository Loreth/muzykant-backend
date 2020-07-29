package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.Equipment;
import pl.kamilprzenioslo.muzykant.persistance.entities.EquipmentEntity;

@Mapper
public interface EquipmentMapper extends BaseMapper<Equipment, EquipmentEntity> {}
