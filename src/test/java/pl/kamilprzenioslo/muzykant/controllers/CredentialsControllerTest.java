package pl.kamilprzenioslo.muzykant.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Collections;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit5.annotation.FlywayTestExtension;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.kamilprzenioslo.muzykant.config.TestSecurityConfiguration;
import pl.kamilprzenioslo.muzykant.dtos.security.PasswordChangeRequest;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.persistance.entities.CredentialsEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.CredentialsRepository;

@Import(TestSecurityConfiguration.class)
@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CredentialsControllerTest {

  @Autowired private TestRestTemplate restTemplate;
  @Autowired private CredentialsRepository credentialsRepository;
  @Autowired HttpHeaders jwtHeaderForBand;
  private final String SERVER_URL;

  public CredentialsControllerTest(@LocalServerPort int port) {
    SERVER_URL = "http://localhost:" + port;
  }

  @FlywayTest
  @Test
  void shouldSignUpCorrectlyAndReturnOkStatus() {
    SignUpRequest signUpRequest = new SignUpRequest("testmail@example.com", "3testpass3");
    LocalDateTime justBeforeRequestDateTime = LocalDateTime.now();

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(SERVER_URL + "/sign-up", signUpRequest, String.class);

    CredentialsEntity credentials =
        credentialsRepository.findByEmailIgnoreCase("testmail@example.com").orElseThrow();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("testmail@example.com", credentials.getEmail());
    assertTrue(new BCryptPasswordEncoder().matches("3testpass3", credentials.getPassword()));
    assertNotNull(credentials.getEmailConfirmation().getToken());
    assertTrue(
        credentials.getEmailConfirmation().getTokenExpiration().isAfter(justBeforeRequestDateTime));
    assertFalse(credentials.isEmailConfirmed());
  }

  @FlywayTest
  @Test
  void shouldConfirmEmailAndReturnOkStatusForExistingToken() {
    String token = "6d8e6ea9-8af9-4716-96bc-6f910ef61b76";
    var requestBody = Collections.singletonMap("token", token);

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(SERVER_URL + "/confirm-email", requestBody, String.class);

    CredentialsEntity credentials =
        credentialsRepository
            .findByEmailIgnoreCase("unconfirmedmail@unconfirmed.com")
            .orElseThrow();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertTrue(credentials.isEmailConfirmed());
  }

  @FlywayTest
  @Test
  void shouldReturnConflictStatusForNonExistingToken() {
    String fakeToken = "77455708-1f9b-4643-94f8-206d96b35e63";
    var requestBody = Collections.singletonMap("token", fakeToken);

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(SERVER_URL + "/confirm-email", requestBody, String.class);

    assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldReturnConflictStatusForExpiredToken() {
    String expiredToken = "a163ca90-5e57-4861-9e21-119d26923c63";
    var requestBody = Collections.singletonMap("token", expiredToken);

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(SERVER_URL + "/confirm-email", requestBody, String.class);

    assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldReturnConflictGivenWrongCurrentPasswordForChangePassword() {
    PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest();
    passwordChangeRequest.setCurrentPassword("wrongwrongwrong123");
    passwordChangeRequest.setNewPassword("newPass644p");

    HttpEntity<PasswordChangeRequest> requestEntity =
        new HttpEntity<>(passwordChangeRequest, jwtHeaderForBand);

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(
            SERVER_URL + "/users/8/change-password", requestEntity, String.class);

    assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldReturnOkGivenCorrectCurrentPasswordForChangePassword() {
    PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest();
    passwordChangeRequest.setCurrentPassword("mocnehaslo123");
    passwordChangeRequest.setNewPassword("newPass644p");

    HttpEntity<PasswordChangeRequest> requestEntity =
        new HttpEntity<>(passwordChangeRequest, jwtHeaderForBand);

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(
            SERVER_URL + "/users/8/change-password", requestEntity, String.class);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}
