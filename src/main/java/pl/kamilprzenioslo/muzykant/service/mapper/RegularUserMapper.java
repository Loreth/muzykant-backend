package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.RegularUser;
import pl.kamilprzenioslo.muzykant.persistance.entities.RegularUserEntity;

@Mapper(uses = PersonMapper.class)
public interface RegularUserMapper extends BaseMapper<RegularUser, RegularUserEntity> {}
