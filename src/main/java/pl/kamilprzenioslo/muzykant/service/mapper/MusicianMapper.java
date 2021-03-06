package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.Musician;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianEntity;

@Mapper(uses = PersonMapper.class)
public interface MusicianMapper extends BaseMapper<Musician, MusicianEntity> {}
