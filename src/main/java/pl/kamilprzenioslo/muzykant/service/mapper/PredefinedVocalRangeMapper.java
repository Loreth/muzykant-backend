package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.PredefinedVocalRange;
import pl.kamilprzenioslo.muzykant.persistance.entities.PredefinedVocalRangeEntity;

@Mapper
public interface PredefinedVocalRangeMapper
    extends BaseMapper<PredefinedVocalRange, PredefinedVocalRangeEntity> {}
