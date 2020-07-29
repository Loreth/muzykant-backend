package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.Musician;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianEntity;

public interface MusicianService
    extends CrudService<Musician, Integer>,
        SpecificationService<Musician, MusicianEntity, Integer> {}
