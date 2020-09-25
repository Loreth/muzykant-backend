package pl.kamilprzenioslo.muzykant.service.implementations.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kamilprzenioslo.muzykant.service.StorageService;

@Service
@Profile("dev")
@Slf4j
public class FileSystemStorageService implements StorageService {

  private final Path rootLocation;

  public FileSystemStorageService(StorageProperties storageProperties) {
    this.rootLocation = Path.of(storageProperties.getLocation());
    initialize();
  }

  @Override
  public void store(MultipartFile file, String filename, String extension) {
    log.debug("attempting to store file " + file.getOriginalFilename() + " as " + filename);

    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file " + filename);
      }
      if (filename.contains("..")) {
        throw new StorageException(
            "Cannot store file with relative path outside current directory " + filename);
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(
            inputStream, rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

        log.debug("stored file " + filename);
      }
    } catch (IOException e) {
      throw new StorageException("Failed to store file " + filename, e);
    }
  }

  @Override
  public Resource loadAsResource(String filename) {
    try {
      Path file = load(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageException("Could not read file: " + filename);
      }
    } catch (MalformedURLException e) {
      throw new StorageException("Could not read file: " + filename, e);
    }
  }

  private Path load(String filename) {
    return rootLocation.resolve(filename);
  }

  @Override
  public void delete(String filename) {
    try {
      Files.delete(rootLocation.resolve(filename));
    } catch (IOException e) {
      throw new StorageException("Could not delete file (file not found): " + filename);
    }
  }

  @Override
  public void deleteWithAnyExtension(String filenameWithoutExtension) {
    String[] foundFiles =
        rootLocation.toFile().list((dir, name) -> name.startsWith(filenameWithoutExtension));

    for (String filename : foundFiles) {
      delete(filename);
    }
  }

  private void initialize() {
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }
}
