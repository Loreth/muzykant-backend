package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.PredefinedVocalRange;
import pl.kamilprzenioslo.muzykant.persistance.entities.PredefinedVocalRangeEntity;

public interface PredefinedVocalRangeService
    extends CrudService<PredefinedVocalRange, Integer>,
        SpecificationService<PredefinedVocalRange, PredefinedVocalRangeEntity, Integer> {}
