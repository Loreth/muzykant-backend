package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.Authority;
import pl.kamilprzenioslo.muzykant.persistance.entities.AuthorityEntity;

@Mapper
public interface AuthorityMapper extends BaseMapper<Authority, AuthorityEntity> {}
