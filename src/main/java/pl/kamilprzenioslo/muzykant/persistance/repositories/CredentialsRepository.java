package pl.kamilprzenioslo.muzykant.persistance.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamilprzenioslo.muzykant.persistance.entities.CredentialsEntity;

public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Integer> {
  Optional<CredentialsEntity> findByEmail(String email);
}
