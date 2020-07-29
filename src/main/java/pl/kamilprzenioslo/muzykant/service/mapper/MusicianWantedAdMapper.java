package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.MusicianWantedAd;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianWantedAdEntity;

@Mapper(config = AdMapperConfig.class)
public interface MusicianWantedAdMapper
    extends BaseMapper<MusicianWantedAd, MusicianWantedAdEntity> {}
