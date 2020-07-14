package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianEntity;

public interface MusicianRepository
    extends JpaRepository<MusicianEntity, Integer>, JpaSpecificationExecutor<MusicianEntity> {}
