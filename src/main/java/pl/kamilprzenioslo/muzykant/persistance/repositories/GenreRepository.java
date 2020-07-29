package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.GenreEntity;

public interface GenreRepository
    extends JpaRepository<GenreEntity, Integer>, JpaSpecificationExecutor<GenreEntity> {}
