package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.VocalTechniqueEntity;

public interface VocalTechniqueRepository
    extends JpaRepository<VocalTechniqueEntity, Integer>,
        JpaSpecificationExecutor<VocalTechniqueEntity> {}
