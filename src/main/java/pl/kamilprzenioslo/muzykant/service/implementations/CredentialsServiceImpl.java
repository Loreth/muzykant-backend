package pl.kamilprzenioslo.muzykant.service.implementations;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.exception.exceptions.ConfirmationMailException;
import pl.kamilprzenioslo.muzykant.exception.exceptions.EmailAlreadyTakenException;
import pl.kamilprzenioslo.muzykant.exception.exceptions.EmailConfirmationTokenExpiredException;
import pl.kamilprzenioslo.muzykant.exception.exceptions.EmailConfirmationTokenNotFound;
import pl.kamilprzenioslo.muzykant.exception.exceptions.EmailNotConfirmedException;
import pl.kamilprzenioslo.muzykant.exception.exceptions.UserAlreadyAssignedException;
import pl.kamilprzenioslo.muzykant.persistance.entities.AuthorityEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.CredentialsEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.EmailConfirmationEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.AuthorityRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.CredentialsRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.EmailConfirmationRepository;
import pl.kamilprzenioslo.muzykant.persistance.repositories.UserRepository;
import pl.kamilprzenioslo.muzykant.security.UserAuthority;
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
  public Credentials loadUserByUsername(String username) {
    CredentialsEntity credentialsEntity = repository.findByEmailIgnoreCase(username).orElse(null);

    if (credentialsEntity == null) {
      throw new UsernameNotFoundException(username);
    }

    return mapper.mapToDto(credentialsEntity);
  }

  @Override
  public void signUp(SignUpRequest signUpRequest) throws MessagingException {
    String email = signUpRequest.getEmail();
    log.debug("signing up " + email);
    verifyEmailNotAlreadyTaken(email);

    CredentialsEntity credentialsEntity = new CredentialsEntity();
    credentialsEntity.setEmail(email);
    credentialsEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

    repository.save(credentialsEntity);
    sendConfirmationMail(email);
  }

  private void verifyEmailNotAlreadyTaken(String email) {
    if (repository.existsByEmailIgnoreCase(email)) {
      throw new EmailAlreadyTakenException();
    }
  }

  private void sendConfirmationMail(String email) throws MessagingException {
    UUID confirmationToken = UUID.randomUUID();

    CredentialsEntity credentialsEntity =
        repository.findByEmailIgnoreCase(email).orElseThrow(ConfirmationMailException::new);

    EmailConfirmationEntity emailConfirmation = credentialsEntity.getEmailConfirmation();
    if (emailConfirmation == null) {
      emailConfirmation = new EmailConfirmationEntity();
    }
    emailConfirmation.setToken(confirmationToken);
    emailConfirmation.setTokenExpiration(
        LocalDateTime.now().plusHours(emailConfirmationTokenExpirationH));
    credentialsEntity.setEmailConfirmation(emailConfirmation);
    repository.save(credentialsEntity);

    mailService.sendConfirmationMail(email, confirmationToken);
  }

  @Override
  public void confirmEmail(UUID confirmationToken) {
    EmailConfirmationEntity emailConfirmation =
        emailConfirmationRepository
            .findByToken(confirmationToken)
            .orElseThrow(EmailConfirmationTokenNotFound::new);

    verifyTokenExpiration(emailConfirmation);
    CredentialsEntity credentials = emailConfirmation.getCredentials();
    credentials.setEmailConfirmation(null);
    repository.save(credentials);
  }

  private void verifyTokenExpiration(EmailConfirmationEntity emailConfirmationEntity) {
    if (emailConfirmationEntity.isTokenExpired()) {
      throw new EmailConfirmationTokenExpiredException();
    }
  }

  @Override
  public void resendConfirmationMail(String email) throws MessagingException {
    CredentialsEntity credentialsEntity =
        repository.findByEmailIgnoreCase(email).orElseThrow(ConfirmationMailException::new);

    if (credentialsEntity.isEmailConfirmed()) {
      throw new ConfirmationMailException();
    }

    sendConfirmationMail(email);
  }

  @Override
  public void assignUserProfileToCurrentlyAuthenticatedUser(
      UserAuthority userAuthority, Integer userId) {
    Credentials principal =
        (Credentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    CredentialsEntity credentialsEntity =
        repository.findByEmailIgnoreCase(principal.getEmail()).orElseThrow();

    verifyUserNotAlreadyAssigned(credentialsEntity);
    verifyEmailConfirmation(credentialsEntity);

    AuthorityEntity authorityEntity = authorityRepository.findByUserAuthority(userAuthority);
    credentialsEntity.setAuthority(authorityEntity);
    credentialsEntity.setUser(userRepository.getOne(userId));

    repository.save(credentialsEntity);
  }

  private void verifyUserNotAlreadyAssigned(CredentialsEntity credentialsEntity) {
    if (credentialsEntity.getUser() != null) {
      throw new UserAlreadyAssignedException();
    }
  }

  private void verifyEmailConfirmation(CredentialsEntity credentialsEntity) {
    if (!credentialsEntity.isEmailConfirmed()) {
      throw new EmailNotConfirmedException();
    }
  }
}
