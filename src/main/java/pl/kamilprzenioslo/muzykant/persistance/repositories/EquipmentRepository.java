package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamilprzenioslo.muzykant.persistance.entities.EquipmentEntity;

public interface EquipmentRepository extends JpaRepository<EquipmentEntity, Integer> {}
