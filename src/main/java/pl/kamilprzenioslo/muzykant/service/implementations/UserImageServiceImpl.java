package pl.kamilprzenioslo.muzykant.service.implementations;

import java.util.List;
import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.UserImage;
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

  private final UserRepository userRepository;
  private final StorageService storageService;

  public UserImageServiceImpl(
      UserImageRepository repository,
      UserRepository userRepository,
      BaseMapper<UserImage, UserImageEntity> mapper,
      StorageService storageService) {
    super(repository, mapper);
    this.userRepository = userRepository;
    this.storageService = storageService;
  }

  @Override
  public List<UserImage> findByUserId(int userId) {
    return mapper.mapToDtoList(repository.findByUser_Id(userId));
  }

  @Override
  public void saveProfileImage(String fileUri, int userId) {
    userRepository.findById(userId).orElseThrow().setProfileImageLink(fileUri);
  }

  @Override
  public void deleteById(Integer id) {
    repository.findById(id).ifPresent(entity -> storageService.delete(entity.getFilename()));
    super.deleteById(id);
  }
}
