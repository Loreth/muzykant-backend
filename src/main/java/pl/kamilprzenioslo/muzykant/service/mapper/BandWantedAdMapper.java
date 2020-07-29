package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.BandWantedAd;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandWantedAdEntity;

@Mapper(config = AdMapperConfig.class)
public interface BandWantedAdMapper extends BaseMapper<BandWantedAd, BandWantedAdEntity> {}
