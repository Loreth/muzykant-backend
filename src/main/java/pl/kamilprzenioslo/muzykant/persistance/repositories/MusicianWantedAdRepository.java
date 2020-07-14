package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianWantedAdEntity;

public interface MusicianWantedAdRepository
    extends JpaRepository<MusicianWantedAdEntity, Integer>,
        JpaSpecificationExecutor<MusicianWantedAdEntity> {}
