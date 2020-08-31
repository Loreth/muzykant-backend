package pl.kamilprzenioslo.muzykant.persistance.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamilprzenioslo.muzykant.persistance.entities.EmailConfirmationEntity;

public interface EmailConfirmationRepository
    extends JpaRepository<EmailConfirmationEntity, Integer> {
  Optional<EmailConfirmationEntity> findByToken(UUID token);
}
