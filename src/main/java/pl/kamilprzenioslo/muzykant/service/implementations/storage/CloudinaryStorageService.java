package pl.kamilprzenioslo.muzykant.service.implementations.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kamilprzenioslo.muzykant.service.StorageService;

@Service
@Profile("prod")
@AllArgsConstructor
@Slf4j
public class CloudinaryStorageService implements StorageService {
  private final Cloudinary cloudinary;
  private final @Value("${app.storage.cloudinary.image-dir}") String cloudinaryImgDir;

  @Override
  public void store(MultipartFile file, String filename, String extension) {
    try {
      cloudinary
          .uploader()
          .upload(
              file.getBytes(),
              ObjectUtils.asMap(
                  "resource_type", "auto", "folder", "image-uploads", "public_id", filename));
    } catch (IOException e) {
      throw new StorageException("Failed to store file " + filename, e);
    }
  }

  @Override
  public Resource loadAsResource(String filename) {
    return null;
  }

  @Override
  public void delete(String filename) {
    try {
      String deletePath = cloudinaryImgDir + "/" + removeExtension(filename);
      cloudinary.uploader().destroy(deletePath, ObjectUtils.asMap("invalidate", true));
    } catch (IOException e) {
      throw new StorageException("Failed to delete file " + filename, e);
    }
  }

  @Override
  public void deleteWithAnyExtension(String filenameWithoutExtension) {
    delete(filenameWithoutExtension);
  }

  private String removeExtension(String filename) {
    int indexOfExtension = filename.lastIndexOf(".");
    if (indexOfExtension != -1) {
      filename = filename.substring(0, indexOfExtension);
    }
    return filename;
  }
}
