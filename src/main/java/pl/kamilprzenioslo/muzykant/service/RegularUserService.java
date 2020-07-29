package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.RegularUser;
import pl.kamilprzenioslo.muzykant.persistance.entities.RegularUserEntity;

public interface RegularUserService
    extends CrudService<RegularUser, Integer>,
        SpecificationService<RegularUser, RegularUserEntity, Integer> {}
