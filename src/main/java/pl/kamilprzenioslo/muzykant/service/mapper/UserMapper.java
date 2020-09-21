package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.User;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;

@Mapper(uses = {UserImageMapper.class, SocialMediaLinksMapper.class})
public interface UserMapper extends BaseMapper<User, UserEntity> {}
