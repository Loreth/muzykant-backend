package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamilprzenioslo.muzykant.persistance.entities.PredefinedVocalRangeEntity;

public interface PredefinedVocalRangeRepository
    extends JpaRepository<PredefinedVocalRangeEntity, Integer> {}
