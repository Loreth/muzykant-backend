package pl.kamilprzenioslo.muzykant.service;

import java.util.List;
import pl.kamilprzenioslo.muzykant.dtos.UserImage;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserImageEntity;

public interface UserImageService
    extends CrudService<UserImage, Integer>,
        SpecificationService<UserImage, UserImageEntity, Integer> {
  List<UserImage> findByUserId(int userId);

  void saveProfileImage(String fileUri, int userId);
}
