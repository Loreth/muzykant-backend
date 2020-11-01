package pl.kamilprzenioslo.muzykant.service.implementations;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kamilprzenioslo.muzykant.dtos.UserImage;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserImageEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.UserImageRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.UserRepository;
import pl.kamilprzenioslo.muzykant.service.StorageService;
import pl.kamilprzenioslo.muzykant.service.UserImageService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class UserImageServiceImpl
    extends BaseSpecificationCrudService<UserImage, UserImageEntity, Integer, UserImageRepository>
    implements UserImageService {
  private static final String PROFILE_IMAGE_SUFFIX = "profile-image";
  private static final String USER_ID_IMAGE_NAME_SEPARATOR = "_";

  private final UserRepository userRepository;
  private final StorageService storageService;
  private final String imageDownloadUri;

  public UserImageServiceImpl(
      UserImageRepository repository,
      UserRepository userRepository,
      BaseMapper<UserImage, UserImageEntity> mapper,
      StorageService storageService,
      String imageDownloadUri) {
    super(repository, mapper);
    this.userRepository = userRepository;
    this.storageService = storageService;
    this.imageDownloadUri = imageDownloadUri;
  }

  @Override
  public UserImage saveImage(MultipartFile image, int userId, int orderIndex) {
    return super.save(storeImage(image, userId, orderIndex, false));
  }

  private UserImage storeImage(
      MultipartFile image, int userId, int orderIndex, boolean profileImage) {
    String fileName = makeFileName(userId, profileImage);
    String extension = makeFileExtension(image);
    String fileUri = imageDownloadUri + "/" + fileName + extension;

    storageService.store(image, fileName, extension);
    return new UserImage(fileName + extension, fileUri, userId, orderIndex);
  }

  private String makeFileName(int userId, boolean profileImage) {
    String filename = userId + USER_ID_IMAGE_NAME_SEPARATOR;
    if (profileImage) {
      filename += PROFILE_IMAGE_SUFFIX;
    } else {
      filename += UUID.randomUUID().toString();
    }
    return filename;
  }

  private String makeFileExtension(MultipartFile file) {
    int indexOfExtension = file.getOriginalFilename().lastIndexOf(".");
    if (indexOfExtension != -1) {
      return file.getOriginalFilename().substring(indexOfExtension);
    } else {
      return "." + file.getContentType().substring(file.getContentType().lastIndexOf('/') + 1);
    }
  }

  @Override
  public UserImage saveNewProfileImage(MultipartFile image, int userId) {
    UserEntity userEntity = userRepository.findById(userId).orElseThrow();
    storageService.deleteWithAnyExtension(makeFileName(userEntity.getId(), true));

    UserImage storedImage = storeImage(image, userId, 0, true);
    userEntity.setProfileImageLink(storedImage.getLink());
    userRepository.save(userEntity);

    return storedImage;
  }

  @Override
  public void deleteById(Integer id) {
    repository.findById(id).ifPresent(entity -> storageService.delete(entity.getFilename()));
    super.deleteById(id);
  }
}
