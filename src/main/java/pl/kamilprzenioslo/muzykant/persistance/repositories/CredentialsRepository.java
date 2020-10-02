package pl.kamilprzenioslo.muzykant.persistance.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamilprzenioslo.muzykant.persistance.entities.CredentialsEntity;

public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Integer> {

  Optional<CredentialsEntity> findByEmailIgnoreCase(String email);

  boolean existsByEmailIgnoreCase(String email);

  Optional<CredentialsEntity> findByUser_LinkName(String linkName);
}
