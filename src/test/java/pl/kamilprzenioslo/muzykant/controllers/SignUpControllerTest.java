package pl.kamilprzenioslo.muzykant.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.UUID;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit5.annotation.FlywayTestExtension;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.kamilprzenioslo.muzykant.dtos.security.SignUpRequest;
import pl.kamilprzenioslo.muzykant.persistance.entities.EmailConfirmationEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.EmailConfirmationRepository;

@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SignUpControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private EmailConfirmationRepository emailConfirmationRepository;
  @Autowired
  private ObjectMapper objectMapper;
  private final String SERVER_URL;

  public SignUpControllerTest(@LocalServerPort int port) {
    SERVER_URL = "http://localhost:" + port;
  }

  @Test
  void shouldSignUpCorrectlyAndReturnOkStatus() {
    SignUpRequest signUpRequest = new SignUpRequest("testmail@somesite.com", "3testpass3");
    LocalDateTime justBeforeRequestDateTime = LocalDateTime.now();

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(SERVER_URL + "/sign-up", signUpRequest, String.class);
    EmailConfirmationEntity emailConfirmation =
        emailConfirmationRepository.findByEmailIgnoreCase("testmail@somesite.com").orElseThrow();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("testmail@somesite.com", emailConfirmation.getEmail());
    assertTrue(new BCryptPasswordEncoder().matches("3testpass3", emailConfirmation.getPassword()));
    assertNotNull(emailConfirmation.getToken());
    assertTrue(emailConfirmation.getTokenExpiration().isAfter(justBeforeRequestDateTime));
    assertFalse(emailConfirmation.isConfirmed());
  }

  @Test
  void shouldConfirmEmailAndReturnOkStatusForExistingToken() {
    UUID token = UUID.fromString("6d8e6ea9-8af9-4716-96bc-6f910ef61b76");
    LocalDateTime justBeforeRequestDateTime = LocalDateTime.now();

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(SERVER_URL + "/confirm-email", token, String.class);
    EmailConfirmationEntity emailConfirmation =
        emailConfirmationRepository
            .findByEmailIgnoreCase("unconfirmedmail@unconfirmed.com")
            .orElseThrow();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(token, emailConfirmation.getToken());
    assertTrue(emailConfirmation.getTokenExpiration().isAfter(justBeforeRequestDateTime));
    assertTrue(emailConfirmation.isConfirmed());
  }

  @Test
  void shouldReturnConflictStatusForNonExistingToken() {
    UUID fakeToken = UUID.fromString("77455708-1f9b-4643-94f8-206d96b35e63");

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(SERVER_URL + "/confirm-email", fakeToken, String.class);

    assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
  }

  @Test
  void shouldReturnConflictStatusForExpiredTokenAndDeleteCorrespondingEmailConfirmation() {
    UUID expiredToken = UUID.fromString("a163ca90-5e57-4861-9e21-119d26923c63");

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(SERVER_URL + "/confirm-email", expiredToken, String.class);

    assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    assertTrue(emailConfirmationRepository.findByToken(expiredToken).isEmpty());
  }
}
