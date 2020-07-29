package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.VocalRange;
import pl.kamilprzenioslo.muzykant.persistance.entities.VocalRangeEntity;

@Mapper
public interface VocalRangeMapper extends BaseMapper<VocalRange, VocalRangeEntity> {}
