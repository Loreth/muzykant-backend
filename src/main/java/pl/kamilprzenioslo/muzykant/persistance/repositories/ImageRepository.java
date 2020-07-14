package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamilprzenioslo.muzykant.persistance.entities.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {}
