package pl.kamilprzenioslo.muzykant.security;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.persistance.repositories.UserRepository;
import pl.kamilprzenioslo.muzykant.service.EquipmentService;
import pl.kamilprzenioslo.muzykant.service.UserImageService;

@RequiredArgsConstructor
@Component
public class AccessSecurity {

  private final UserImageService userImageService;
  private final UserRepository userRepository;
  private final EquipmentService equipmentService;

  public boolean hasFullAccessToUserResource(Authentication authentication, int userId) {
    Credentials principal = (Credentials) authentication.getPrincipal();

    return principal.getUserId() == userId;
  }

  public boolean hasFullAccessToUserImage(Authentication authentication, int imageId) {
    Credentials principal = (Credentials) authentication.getPrincipal();

    return userImageService
        .findById(imageId)
        .map(userImage -> userImage.getUserId().equals(principal.getUserId()))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @Transactional
  public boolean hasFullAccessToAd(Authentication authentication, int adId) {
    Credentials principal = (Credentials) authentication.getPrincipal();

    return userRepository
        .findById(principal.getUserId())
        .map(user -> user.getAds().stream().map(Persistable::getId).anyMatch(id -> id == adId))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public boolean hasFullAccessToEquipment(Authentication authentication, int equipmentId) {
    Credentials principal = (Credentials) authentication.getPrincipal();

    return equipmentService
        .findById(equipmentId)
        .map(equipment -> equipment.getMusicianId().equals(principal.getUserId()))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
