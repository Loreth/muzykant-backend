package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.Instrument;
import pl.kamilprzenioslo.muzykant.persistance.entities.InstrumentEntity;

@Mapper
public interface InstrumentMapper extends BaseMapper<Instrument, InstrumentEntity> {}
