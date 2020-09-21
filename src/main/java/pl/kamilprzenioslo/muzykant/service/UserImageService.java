package pl.kamilprzenioslo.muzykant.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import pl.kamilprzenioslo.muzykant.dtos.UserImage;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserImageEntity;

public interface UserImageService
    extends CrudService<UserImage, Integer>,
        SpecificationService<UserImage, UserImageEntity, Integer> {
  List<UserImage> findByUserId(int userId);

  UserImage save(MultipartFile image, UserImage dto);

  void saveNewProfileImage(MultipartFile image, String filename, String fileUri, int userId);
}
