package pl.kamilprzenioslo.muzykant.controllers;

import java.util.UUID;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
  public void confirmEmail(@RequestBody UUID confirmationToken) {
    credentialsService.confirmEmail(confirmationToken);
  }
}
