package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.persistance.entities.CredentialsEntity;

@Mapper
public interface CredentialsMapper extends BaseMapper<Credentials, CredentialsEntity> {}
