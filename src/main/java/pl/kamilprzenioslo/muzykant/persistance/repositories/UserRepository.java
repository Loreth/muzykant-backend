package pl.kamilprzenioslo.muzykant.persistance.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;

public interface UserRepository
    extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {

  Optional<UserEntity> findByLinkName(String linkName);
}
