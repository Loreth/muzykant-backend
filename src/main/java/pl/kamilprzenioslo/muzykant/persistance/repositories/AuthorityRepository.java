package pl.kamilprzenioslo.muzykant.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamilprzenioslo.muzykant.persistance.entities.AuthorityEntity;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {
  AuthorityEntity findByUserAuthority(UserAuthority userAuthority);
}
