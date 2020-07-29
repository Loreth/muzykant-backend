package pl.kamilprzenioslo.muzykant.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.Equipment;
import pl.kamilprzenioslo.muzykant.persistance.entities.EquipmentEntity;
import pl.kamilprzenioslo.muzykant.service.EquipmentService;
import pl.kamilprzenioslo.muzykant.specifications.EquipmentSpecification;

@RestController
@RequestMapping(RestMappings.EQUIPMENT)
public class EquipmentController
    extends SpecificationRestController<
        Equipment, EquipmentEntity, Integer, EquipmentSpecification, EquipmentService> {

  public EquipmentController(EquipmentService service) {
    super(service);
  }
}
