package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandEntity;

public interface BandRepository
    extends JpaRepository<BandEntity, Integer>, JpaSpecificationExecutor<BandEntity> {}
