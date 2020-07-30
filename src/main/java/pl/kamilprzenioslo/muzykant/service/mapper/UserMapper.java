package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.MapperConfig;
import pl.kamilprzenioslo.muzykant.dtos.User;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;

@MapperConfig(uses = ImageMapper.class)
public interface UserMapper extends BaseMapper<User, UserEntity> {}
