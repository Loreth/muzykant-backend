package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.Band;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandEntity;

public interface BandService
    extends SpecificationService<Band, BandEntity, Integer>, CrudService<Band, Integer> {}
