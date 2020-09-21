package pl.kamilprzenioslo.muzykant.controllers;

import java.net.URI;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriTemplate;
import pl.kamilprzenioslo.muzykant.dtos.UserImage;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserImageEntity;
import pl.kamilprzenioslo.muzykant.service.StorageService;
import pl.kamilprzenioslo.muzykant.service.UserImageService;
import pl.kamilprzenioslo.muzykant.specifications.UserImageSpecification;

@RestController
@RequestMapping(RestMappings.USER_IMAGE)
public class UserImageController
    extends SpecificationRestController<
        UserImage, UserImageEntity, Integer, UserImageSpecification, UserImageService> {

  private final UserImageService userImageService;
  private final StorageService storageService;

  public UserImageController(UserImageService userImageService, StorageService storageService) {
    super(userImageService);
    this.userImageService = userImageService;
    this.storageService = storageService;
  }

  @PostMapping(RestMappings.IMAGE_UPLOAD)
  public ResponseEntity<UserImage> uploadUserImage(
      @RequestParam("file") MultipartFile file,
      @RequestParam("userId") int userId,
      @RequestParam(value = "profileImage", required = false, defaultValue = "false")
          boolean profileImage,
      @RequestParam("orderIndex") int orderIndex,
      HttpServletRequest request) {
    String filename = userId + "_";
    if (profileImage) {
      filename += "profile-image";
    } else {
      filename += UUID.randomUUID().toString();
    }
    filename += file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

    String fileUri =
        ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(RestMappings.USER_IMAGE + RestMappings.IMAGE_UPLOADS + "/")
            .path(filename)
            .toUriString();

    storageService.store(file, filename);

    if (profileImage) {
      userImageService.saveProfileImage(fileUri, userId);
      return ResponseEntity.ok().build();
    } else {
      UserImage savedUserImage = userImageService.save(new UserImage(filename, fileUri, userId, orderIndex));
      URI entityMapping =
          new UriTemplate(request.getRequestURI() + RestMappings.ID).expand(savedUserImage.getId());

      return ResponseEntity.created(entityMapping).contentType(MediaType.APPLICATION_JSON).body(savedUserImage);
    }
  }

  @GetMapping(RestMappings.IMAGE_UPLOADS + "/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> downloadUserImage(@PathVariable String filename) {
    Resource resource = storageService.loadAsResource(filename);

    return ResponseEntity.ok()
        .contentType(
            MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }
}
