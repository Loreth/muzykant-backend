package pl.kamilprzenioslo.muzykant.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  void store(MultipartFile file, String filename, String extension);

  Resource loadAsResource(String filename);

  void delete(String filename);

  void deleteWithAnyExtension(String filenameWithoutExtension);
}
