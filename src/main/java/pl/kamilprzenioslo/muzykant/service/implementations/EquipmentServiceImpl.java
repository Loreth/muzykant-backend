package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.Equipment;
import pl.kamilprzenioslo.muzykant.persistance.entities.EquipmentEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.EquipmentRepository;
import pl.kamilprzenioslo.muzykant.service.EquipmentService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class EquipmentServiceImpl
    extends BaseSpecificationCrudService<Equipment, EquipmentEntity, Integer, EquipmentRepository>
    implements EquipmentService {

  public EquipmentServiceImpl(
      EquipmentRepository repository, BaseMapper<Equipment, EquipmentEntity> mapper) {
    super(repository, mapper);
  }
}
