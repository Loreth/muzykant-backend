package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.JamSessionAd;
import pl.kamilprzenioslo.muzykant.persistance.entities.JamSessionAdEntity;

public interface JamSessionAdService
    extends CrudService<JamSessionAd, Integer>,
        SpecificationService<JamSessionAd, JamSessionAdEntity, Integer> {}
