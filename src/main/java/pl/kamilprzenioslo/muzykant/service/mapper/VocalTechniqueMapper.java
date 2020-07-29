package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.VocalTechnique;
import pl.kamilprzenioslo.muzykant.persistance.entities.VocalTechniqueEntity;

@Mapper
public interface VocalTechniqueMapper extends BaseMapper<VocalTechnique, VocalTechniqueEntity> {}
