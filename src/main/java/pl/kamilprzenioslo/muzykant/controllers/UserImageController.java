package pl.kamilprzenioslo.muzykant.controllers;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
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
import org.springframework.web.util.UriTemplate;
import pl.kamilprzenioslo.muzykant.controllers.mappings.RestMappings;
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
    if (profileImage) {
      UserImage userImage = userImageService.saveNewProfileImage(file, userId);
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userImage);
    } else {
      UserImage savedUserImage = userImageService.saveImage(file, userId, orderIndex);
      URI entityMapping =
          new UriTemplate(request.getRequestURI() + RestMappings.ID).expand(savedUserImage.getId());

      return ResponseEntity.created(entityMapping)
          .contentType(MediaType.APPLICATION_JSON)
          .body(savedUserImage);
    }
  }

  @Profile("dev")
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
