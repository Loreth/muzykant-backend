package pl.kamilprzenioslo.muzykant.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import pl.kamilprzenioslo.muzykant.dtos.UserImage;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserImageEntity;

public interface UserImageService
    extends CrudService<UserImage, Integer>,
        SpecificationService<UserImage, UserImageEntity, Integer> {
  List<UserImage> findByUserId(int userId);

  UserImage saveImage(MultipartFile image, String fileBaseUri, int userId, int orderIndex);

  UserImage saveNewProfileImage(MultipartFile image, String fileBaseUri, int userId);
}
