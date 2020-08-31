package pl.kamilprzenioslo.muzykant.controllers;

import java.util.Map;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;

@RequiredArgsConstructor
@RestController
public class SignUpController {

  private final CredentialsService credentialsService;

  @PostMapping(RestMappings.SIGN_UP)
  public void signUp(@RequestBody SignUpRequest signUpRequest) throws MessagingException {
    credentialsService.signUp(signUpRequest);
  }

  @PostMapping(RestMappings.CONFIRM_EMAIL)
  public void confirmEmail(@RequestBody @NotNull Map<String, String> confirmationToken) {
    try {
      credentialsService.confirmEmail(UUID.fromString(confirmationToken.get("token")));
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY, "Given token is not a valid UUID", ex);
    }
  }
}
