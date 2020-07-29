package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.BandWantedAd;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandWantedAdEntity;

public interface BandWantedAdService
    extends CrudService<BandWantedAd, Integer>,
        SpecificationService<BandWantedAd, BandWantedAdEntity, Integer> {}
