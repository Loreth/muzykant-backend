package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandWantedAdEntity;

public interface BandWantedAdRepository
    extends JpaRepository<BandWantedAdEntity, Integer>,
        JpaSpecificationExecutor<BandWantedAdEntity> {}
