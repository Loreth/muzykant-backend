package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.InstrumentEntity;

public interface InstrumentRepository
    extends JpaRepository<InstrumentEntity, Integer>, JpaSpecificationExecutor<InstrumentEntity> {}
