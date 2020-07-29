package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.JamSessionAd;
import pl.kamilprzenioslo.muzykant.persistance.entities.JamSessionAdEntity;

@Mapper(config = AdMapperConfig.class)
public interface JamSessionAdMapper extends BaseMapper<JamSessionAd, JamSessionAdEntity> {}
