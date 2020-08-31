package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.persistance.entities.CredentialsEntity;

@Mapper(uses = EntityReferenceFactory.class)
public interface CredentialsMapper extends BaseMapper<Credentials, CredentialsEntity> {

  @Mapping(target = "emailConfirmationId", source = "emailConfirmation.id")
  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "linkName", source = "user.linkName")
  @Override
  Credentials mapToDto(CredentialsEntity entity);

  @Mapping(target = "emailConfirmation", source = "emailConfirmationId")
  @Mapping(target = "user", source = "userId")
  @Override
  CredentialsEntity mapToEntity(Credentials dto);
}
