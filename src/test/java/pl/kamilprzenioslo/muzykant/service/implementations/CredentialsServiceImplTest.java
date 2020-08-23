package pl.kamilprzenioslo.muzykant.service.implementations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kamilprzenioslo.muzykant.dtos.Musician;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.persistance.entities.AuthorityEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.CredentialsEntity;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;
import pl.kamilprzenioslo.muzykant.persistance.repositories.AuthorityRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.CredentialsRepository;

@ExtendWith(MockitoExtension.class)
class CredentialsServiceImplTest {
  @Mock CredentialsRepository credentialsRepository;
  @Mock AuthorityRepository authorityRepository;
  @Mock PasswordEncoder passwordEncoder;
  @InjectMocks CredentialsServiceImpl credentialsService;

  @Test
  void shouldSignUpCorrectlyAndSaveCredentialsToRepository() {
    SignUpRequest<Musician> signUpRequest =
        new SignUpRequest<>("email@email.com", "mocnehaslo123", new Musician());
    AuthorityEntity authorityEntity = new AuthorityEntity();
    authorityEntity.setUserAuthority(UserAuthority.ROLE_MUSICIAN);
    authorityEntity.setId(1);
    given(authorityRepository.findByUserAuthority(UserAuthority.ROLE_MUSICIAN))
        .willReturn(authorityEntity);
    given(passwordEncoder.encode("mocnehaslo123"))
        .willReturn(new BCryptPasswordEncoder().encode("mocnehaslo123"));

    credentialsService.signUp(signUpRequest, UserAuthority.ROLE_MUSICIAN, 1);

    CredentialsEntity expectedCredentialsEntity = new CredentialsEntity();
    expectedCredentialsEntity.setEmail(signUpRequest.getEmail());
    expectedCredentialsEntity.setPassword(
        "$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC");
    expectedCredentialsEntity.setAuthority(authorityEntity);
    expectedCredentialsEntity.setUserId(1);
    verify(credentialsRepository).save(expectedCredentialsEntity);
  }
}
