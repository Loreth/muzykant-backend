package pl.kamilprzenioslo.muzykant.controllers;

import java.util.Map;
import java.util.UUID;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.kamilprzenioslo.muzykant.dtos.security.PasswordChangeRequest;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;

@RequiredArgsConstructor
@RestController
public class CredentialsController {

  private final CredentialsService credentialsService;

  @PostMapping(RestMappings.SIGN_UP)
  public void signUp(@RequestBody SignUpRequest signUpRequest) throws MessagingException {
    credentialsService.signUp(signUpRequest);
  }

  @PostMapping(RestMappings.CONFIRM_EMAIL)
  public void confirmEmail(@RequestBody Map<String, String> confirmationToken) {
    try {
      credentialsService.confirmEmail(UUID.fromString(confirmationToken.get("token")));
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(
          HttpStatus.UNPROCESSABLE_ENTITY, "Given token is not a valid UUID", ex);
    }
  }

  @PostMapping(RestMappings.RESEND_MAIL)
  public void resendMail(@RequestBody Map<String, String> email) throws MessagingException {
    credentialsService.resendConfirmationMail(email.get("email"));
  }

  @PostMapping(RestMappings.CHANGE_USER_PASSWORD)
  public void changePassword(
      @PathVariable("id") Integer userId,
      @RequestBody PasswordChangeRequest passwordChangeRequest) {
    credentialsService.changePassword(userId, passwordChangeRequest);
  }
}
