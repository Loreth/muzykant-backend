package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.Band;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandEntity;

@Mapper(config = UserMapper.class)
public interface BandMapper extends BaseMapper<Band, BandEntity> {}
