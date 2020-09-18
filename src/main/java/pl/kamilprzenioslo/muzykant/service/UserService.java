package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.User;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;

public interface UserService
    extends SpecificationService<User, UserEntity, Integer>, CrudService<User, Integer> {}
