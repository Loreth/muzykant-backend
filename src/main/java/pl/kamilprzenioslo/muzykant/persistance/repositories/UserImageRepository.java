package pl.kamilprzenioslo.muzykant.persistance.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserImageEntity;

public interface UserImageRepository
    extends JpaRepository<UserImageEntity, Integer>, JpaSpecificationExecutor<UserImageEntity> {
  List<UserImageEntity> findByUser_Id(int userId);
}
