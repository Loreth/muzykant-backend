package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.User;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.UserRepository;
import pl.kamilprzenioslo.muzykant.service.UserService;
import pl.kamilprzenioslo.muzykant.service.mapper.UserMapper;

@Service
public class UserServiceImpl
    extends BaseSpecificationCrudService<User, UserEntity, Integer, UserRepository>
    implements UserService {

  public UserServiceImpl(UserRepository repository, UserMapper mapper) {
    super(repository, mapper);
  }
}
