package pl.kamilprzenioslo.muzykant.service.implementations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kamilprzenioslo.muzykant.dtos.Musician;
import pl.kamilprzenioslo.muzykant.dtos.security.VerifiedEmailSignUpRequest;
import pl.kamilprzenioslo.muzykant.persistance.entities.AuthorityEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.CredentialsEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.EmailConfirmationEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.RegularUserEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;
import pl.kamilprzenioslo.muzykant.persistance.repositories.AuthorityRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.CredentialsRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.EmailConfirmationRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class CredentialsServiceImplTest {

  @Mock
  CredentialsRepository credentialsRepository;
  @Mock
  AuthorityRepository authorityRepository;
  @Mock
  UserRepository userRepository;
  @Mock
  PasswordEncoder passwordEncoder;
  @Mock
  EmailConfirmationRepository emailConfirmationRepository;
  @InjectMocks
  CredentialsServiceImpl credentialsService;

  @Test
  void shouldSignUpCorrectlyAndSaveCredentialsAndDeleteEmailConfirmation() {
    VerifiedEmailSignUpRequest<Musician> verifiedEmailSignUpRequest =
        new VerifiedEmailSignUpRequest<>("email@email.com", new Musician());
    AuthorityEntity authorityEntity = new AuthorityEntity();
    authorityEntity.setUserAuthority(UserAuthority.ROLE_MUSICIAN);
    authorityEntity.setId(1);
    UserEntity userEntity = new RegularUserEntity();
    userEntity.setId(1);
    var emailConfirmationEntity = new EmailConfirmationEntity();
    emailConfirmationEntity.setEmail("email@email.com");
    emailConfirmationEntity.setPassword(new BCryptPasswordEncoder().encode("mocnehaslo123"));
    emailConfirmationEntity.setConfirmed(true);
    emailConfirmationEntity.setTokenExpiration(LocalDateTime.of(9999, 12, 31, 23, 59));

    given(authorityRepository.findByUserAuthority(UserAuthority.ROLE_MUSICIAN))
        .willReturn(authorityEntity);
    given(userRepository.getOne(1)).willReturn(userEntity);
    given(emailConfirmationRepository.findByEmailIgnoreCase("email@email.com"))
        .willReturn(Optional.of(emailConfirmationEntity));

    credentialsService.createAccount(verifiedEmailSignUpRequest, UserAuthority.ROLE_MUSICIAN, 1);

    CredentialsEntity expectedCredentialsEntity = new CredentialsEntity();
    expectedCredentialsEntity.setEmail(verifiedEmailSignUpRequest.getEmail());
    expectedCredentialsEntity.setPassword(
        "$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC");
    expectedCredentialsEntity.setAuthority(authorityEntity);
    expectedCredentialsEntity.setUser(userEntity);
    verify(credentialsRepository).save(expectedCredentialsEntity);
    verify(emailConfirmationRepository).delete(emailConfirmationEntity);
  }
}
