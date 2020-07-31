package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.VoivodeshipEntity;

public interface VoivodeshipRepository
    extends JpaRepository<VoivodeshipEntity, Integer>,
        JpaSpecificationExecutor<VoivodeshipEntity> {}
