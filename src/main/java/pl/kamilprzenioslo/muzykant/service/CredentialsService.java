package pl.kamilprzenioslo.muzykant.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;

public interface CredentialsService extends ReadService<Credentials, Integer>, UserDetailsService {
  void signUp(SignUpRequest<?> signUpRequest, UserAuthority userAuthority, Integer userId);
}
