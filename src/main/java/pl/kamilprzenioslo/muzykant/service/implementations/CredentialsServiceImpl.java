package pl.kamilprzenioslo.muzykant.service.implementations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.dtos.security.VerifiedEmailSignUpRequest;
import pl.kamilprzenioslo.muzykant.exception.exceptions.EmailAlreadyTakenException;
import pl.kamilprzenioslo.muzykant.exception.exceptions.EmailConfirmationTokenExpiredException;
import pl.kamilprzenioslo.muzykant.exception.exceptions.EmailConfirmationTokenNotFound;
import pl.kamilprzenioslo.muzykant.exception.exceptions.EmailNotConfirmedException;
import pl.kamilprzenioslo.muzykant.persistance.entities.AuthorityEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.CredentialsEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.EmailConfirmationEntity;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;
import pl.kamilprzenioslo.muzykant.persistance.repositories.AuthorityRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.CredentialsRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.EmailConfirmationRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.UserRepository;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;
import pl.kamilprzenioslo.muzykant.service.MailService;
import pl.kamilprzenioslo.muzykant.service.mapper.CredentialsMapper;

@Slf4j
@Service
public class CredentialsServiceImpl
    extends BaseReadService<Credentials, CredentialsEntity, Integer, CredentialsRepository>
    implements CredentialsService {

  private final PasswordEncoder passwordEncoder;
  private final AuthorityRepository authorityRepository;
  private final UserRepository userRepository;
  private final EmailConfirmationRepository emailConfirmationRepository;
  private final MailService mailService;

  @Value("${app.emailConfirmationTokenExpirationH}")
  private int emailConfirmationTokenExpirationH;

  public CredentialsServiceImpl(
      CredentialsRepository repository,
      CredentialsMapper mapper,
      PasswordEncoder passwordEncoder,
      AuthorityRepository authorityRepository,
      UserRepository userRepository,
      EmailConfirmationRepository emailConfirmationRepository,
      MailService mailService) {
    super(repository, mapper);
    this.passwordEncoder = passwordEncoder;
    this.authorityRepository = authorityRepository;
    this.userRepository = userRepository;
    this.emailConfirmationRepository = emailConfirmationRepository;
    this.mailService = mailService;
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
  public void signUp(SignUpRequest signUpRequest) throws MessagingException {
    String email = signUpRequest.getEmail();
    log.debug("signing up " + email);
    verifyEmailNotAlreadyTaken(email);

    var emailConfirmation = new EmailConfirmationEntity();
    UUID confirmationToken = UUID.randomUUID();
    emailConfirmation.setEmail(email);
    emailConfirmation.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
    emailConfirmation.setToken(confirmationToken);
    emailConfirmation.setTokenExpiration(
        LocalDateTime.now().plusHours(emailConfirmationTokenExpirationH));

    mailService.sendConfirmationMail(email, confirmationToken);
    emailConfirmationRepository.save(emailConfirmation);
  }

  private void verifyEmailNotAlreadyTaken(String email) {
    if (emailConfirmationRepository.existsByEmailIgnoreCase(email)
        || repository.existsByEmailIgnoreCase(email)) {
      throw new EmailAlreadyTakenException();
    }
  }

  @Override
  public void confirmEmail(UUID confirmationToken) {
    EmailConfirmationEntity emailConfirmation =
        emailConfirmationRepository
            .findByToken(confirmationToken)
            .orElseThrow(EmailConfirmationTokenNotFound::new);

    verifyTokenExpiration(emailConfirmation);
    emailConfirmation.setConfirmed(true);
    emailConfirmationRepository.save(emailConfirmation);
  }

  @Override
  public void createAccount(
      VerifiedEmailSignUpRequest<?> verifiedEmailSignUpRequest,
      UserAuthority userAuthority,
      Integer userId) {
    var emailConfirmation = findAndVerifyEmailConfirmation(verifiedEmailSignUpRequest.getEmail());

    AuthorityEntity authorityEntity = authorityRepository.findByUserAuthority(userAuthority);
    CredentialsEntity credentialsEntity = new CredentialsEntity();
    credentialsEntity.setEmail(verifiedEmailSignUpRequest.getEmail());
    credentialsEntity.setPassword(emailConfirmation.getPassword());
    credentialsEntity.setAuthority(authorityEntity);
    credentialsEntity.setUser(userRepository.getOne(userId));

    repository.save(credentialsEntity);
    emailConfirmationRepository.delete(emailConfirmation);
  }

  private EmailConfirmationEntity findAndVerifyEmailConfirmation(String email) {
    EmailConfirmationEntity emailConfirmation =
        emailConfirmationRepository
            .findByEmailIgnoreCase(email)
            .orElseThrow(EntityNotFoundException::new);

    if (!emailConfirmation.isConfirmed()) {
      throw new EmailNotConfirmedException();
    }
    verifyTokenExpiration(emailConfirmation);

    return emailConfirmation;
  }

  private void verifyTokenExpiration(EmailConfirmationEntity emailConfirmationEntity) {
    if (emailConfirmationEntity.isTokenExpired()) {
      emailConfirmationRepository.delete(emailConfirmationEntity);
      throw new EmailConfirmationTokenExpiredException();
    }
  }
}
