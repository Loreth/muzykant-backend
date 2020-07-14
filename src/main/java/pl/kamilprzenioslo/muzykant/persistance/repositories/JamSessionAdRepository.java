package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.JamSessionAdEntity;

public interface JamSessionAdRepository
    extends JpaRepository<JamSessionAdEntity, Integer>,
        JpaSpecificationExecutor<JamSessionAdEntity> {}
