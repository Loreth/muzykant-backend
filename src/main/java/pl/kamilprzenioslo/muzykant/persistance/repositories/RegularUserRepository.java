package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.RegularUserEntity;

public interface RegularUserRepository
    extends JpaRepository<RegularUserEntity, Integer>,
        JpaSpecificationExecutor<RegularUserEntity> {}
