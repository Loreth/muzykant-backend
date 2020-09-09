package pl.kamilprzenioslo.muzykant.service;

import java.util.UUID;
import javax.mail.MessagingException;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.security.UserAuthority;

public interface CredentialsService extends ReadService<Credentials, Integer>, UserDetailsService {

  void signUp(SignUpRequest signUpRequest) throws MessagingException;

  void confirmEmail(UUID confirmationToken);

  void resendConfirmationMail(String email) throws MessagingException;

  void assignUserProfileToCurrentlyAuthenticatedUser(UserAuthority userAuthority, Integer userId);
}
