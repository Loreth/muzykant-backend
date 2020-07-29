package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.Equipment;
import pl.kamilprzenioslo.muzykant.persistance.entities.EquipmentEntity;

public interface EquipmentService
    extends CrudService<Equipment, Integer>,
        SpecificationService<Equipment, EquipmentEntity, Integer> {}
