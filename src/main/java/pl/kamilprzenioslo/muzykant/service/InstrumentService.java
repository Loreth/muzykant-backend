package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.Instrument;
import pl.kamilprzenioslo.muzykant.persistance.entities.InstrumentEntity;

public interface InstrumentService
    extends CrudService<Instrument, Integer>,
        SpecificationService<Instrument, InstrumentEntity, Integer> {}
