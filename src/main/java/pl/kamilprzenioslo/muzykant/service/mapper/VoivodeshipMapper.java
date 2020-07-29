package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.Voivodeship;
import pl.kamilprzenioslo.muzykant.persistance.entities.VoivodeshipEntity;

@Mapper
public interface VoivodeshipMapper extends BaseMapper<Voivodeship, VoivodeshipEntity> {}
