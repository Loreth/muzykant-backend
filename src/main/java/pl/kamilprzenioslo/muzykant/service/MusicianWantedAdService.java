package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.MusicianWantedAd;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianWantedAdEntity;

public interface MusicianWantedAdService
    extends CrudService<MusicianWantedAd, Integer>,
        SpecificationService<MusicianWantedAd, MusicianWantedAdEntity, Integer> {}
