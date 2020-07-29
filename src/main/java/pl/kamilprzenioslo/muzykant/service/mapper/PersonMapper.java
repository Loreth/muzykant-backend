package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.Person;
import pl.kamilprzenioslo.muzykant.persistance.entities.PersonEntity;

@Mapper
public interface PersonMapper extends BaseMapper<Person, PersonEntity> {}
