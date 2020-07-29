package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.RegularUser;
import pl.kamilprzenioslo.muzykant.persistance.entities.RegularUserEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.RegularUserRepository;
import pl.kamilprzenioslo.muzykant.service.RegularUserService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class RegularUserServiceImpl
    extends BaseSpecificationCrudService<
        RegularUser, RegularUserEntity, Integer, RegularUserRepository>
    implements RegularUserService {

  public RegularUserServiceImpl(
      RegularUserRepository repository, BaseMapper<RegularUser, RegularUserEntity> mapper) {
    super(repository, mapper);
  }
}
