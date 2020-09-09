package pl.kamilprzenioslo.muzykant.service.implementations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.persistance.entities.AuthorityEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.CredentialsEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.RegularUserEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.AuthorityRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.CredentialsRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.UserRepository;
import pl.kamilprzenioslo.muzykant.security.UserAuthority;

@ExtendWith(MockitoExtension.class)
class CredentialsServiceImplTest {

  @Mock
  CredentialsRepository credentialsRepository;
  @Mock
  AuthorityRepository authorityRepository;
  @Mock
  UserRepository userRepository;
  Authentication authentication = mock(Authentication.class);
  SecurityContext securityContext = mock(SecurityContext.class);
  @InjectMocks
  CredentialsServiceImpl credentialsService;

  @Test
  void shouldAssignUserProfileCorrectlyForCredentialsWithConfirmedEmail() {
    String email = "email@email.com";
    AuthorityEntity authorityEntity = new AuthorityEntity();
    authorityEntity.setUserAuthority(UserAuthority.ROLE_MUSICIAN);
    authorityEntity.setId(1);
    UserEntity userEntity = new RegularUserEntity();
    userEntity.setId(1);
    var credentialsEntity = new CredentialsEntity();
    credentialsEntity.setEmail("email@email.com");
    credentialsEntity.setPassword(new BCryptPasswordEncoder().encode("mocnehaslo123"));

    Credentials credentials = new Credentials();
    credentials.setEmail(credentialsEntity.getEmail());
    credentials.setPassword(credentialsEntity.getPassword());

    SecurityContextHolder.setContext(securityContext);

    given(authorityRepository.findByUserAuthority(UserAuthority.ROLE_MUSICIAN))
        .willReturn(authorityEntity);
    given(userRepository.getOne(1)).willReturn(userEntity);
    given(credentialsRepository.findByEmailIgnoreCase("email@email.com"))
        .willReturn(Optional.of(credentialsEntity));
    given(SecurityContextHolder.getContext().getAuthentication()).willReturn(authentication);
    given(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .willReturn(credentials);

    credentialsService.assignUserProfileToCurrentlyAuthenticatedUser(
        UserAuthority.ROLE_MUSICIAN, 1);

    CredentialsEntity expectedCredentialsEntity = new CredentialsEntity();
    expectedCredentialsEntity.setEmail(email);
    expectedCredentialsEntity.setPassword(
        "$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC");
    expectedCredentialsEntity.setAuthority(authorityEntity);
    expectedCredentialsEntity.setUser(userEntity);
    verify(credentialsRepository).save(expectedCredentialsEntity);
  }
}
