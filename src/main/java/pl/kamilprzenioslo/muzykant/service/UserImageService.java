package pl.kamilprzenioslo.muzykant.service;

import org.springframework.web.multipart.MultipartFile;
import pl.kamilprzenioslo.muzykant.dtos.UserImage;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserImageEntity;

public interface UserImageService
    extends CrudService<UserImage, Integer>,
        SpecificationService<UserImage, UserImageEntity, Integer> {

  UserImage saveImage(MultipartFile image, int userId, int orderIndex);

  UserImage saveNewProfileImage(MultipartFile image, int userId);
}
