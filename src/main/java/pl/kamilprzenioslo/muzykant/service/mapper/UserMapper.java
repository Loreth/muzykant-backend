package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.User;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;

@Mapper
public interface UserMapper extends BaseMapper<User, UserEntity> {}
