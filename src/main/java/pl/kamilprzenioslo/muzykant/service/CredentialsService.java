package pl.kamilprzenioslo.muzykant.service;

import java.util.UUID;
import javax.mail.MessagingException;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.dtos.security.VerifiedEmailSignUpRequest;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;

public interface CredentialsService extends ReadService<Credentials, Integer>, UserDetailsService {

  void signUp(SignUpRequest signUpRequest) throws MessagingException;

  void confirmEmail(UUID confirmationToken);

  void createAccount(
      VerifiedEmailSignUpRequest<?> verifiedEmailSignUpRequest,
      UserAuthority userAuthority,
      Integer userId);

  Credentials findByEmail(String username);
}
