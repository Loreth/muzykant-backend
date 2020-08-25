package pl.kamilprzenioslo.muzykant.service.implementations;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.persistance.entities.AuthorityEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.CredentialsEntity;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;
import pl.kamilprzenioslo.muzykant.persistance.repositories.AuthorityRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.CredentialsRepository;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;
import pl.kamilprzenioslo.muzykant.service.mapper.CredentialsMapper;

@Service
public class CredentialsServiceImpl
    extends BaseReadService<Credentials, CredentialsEntity, Integer, CredentialsRepository>
    implements CredentialsService {
  private final PasswordEncoder passwordEncoder;
  private final AuthorityRepository authorityRepository;

  public CredentialsServiceImpl(
      CredentialsRepository repository,
      CredentialsMapper mapper,
      PasswordEncoder passwordEncoder,
      AuthorityRepository authorityRepository) {
    super(repository, mapper);
    this.passwordEncoder = passwordEncoder;
    this.authorityRepository = authorityRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    CredentialsEntity credentials =
        repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

    return new org.springframework.security.core.userdetails.User(
        credentials.getEmail(), credentials.getPassword(), List.of(credentials.getAuthority()));
  }

  @Override
  public Credentials findByEmail(String username) {
    return mapper.mapToDto(
        repository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(username)));
  }

  @Override
  public void signUp(SignUpRequest<?> signUpRequest, UserAuthority userAuthority, Integer userId) {
    AuthorityEntity authorityEntity = authorityRepository.findByUserAuthority(userAuthority);
    CredentialsEntity credentialsEntity = new CredentialsEntity();
    credentialsEntity.setEmail(signUpRequest.getEmail());
    credentialsEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
    credentialsEntity.setAuthority(authorityEntity);
    credentialsEntity.setUserId(userId);

    repository.save(credentialsEntity);
  }
}
