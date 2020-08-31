package pl.kamilprzenioslo.muzykant.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kamilprzenioslo.muzykant.config.TestSecurityConfiguration;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.dtos.Person;
import pl.kamilprzenioslo.muzykant.dtos.RegularUser;
import pl.kamilprzenioslo.muzykant.dtos.Voivodeship;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;

@Import(TestSecurityConfiguration.class)
@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RegularUserControllerIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private CredentialsService credentialsService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private MultiValueMap<String, String> jwtHeaderForConfirmedCredentialsWithoutCreatedUser;

  private final String RESOURCE_LINK;

  public RegularUserControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/regular-users";
  }

  @FlywayTest
  @Test
  void shouldReturnAllWithGivenParameters() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("name", "ADI")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<RegularUser>>() {});
    List<RegularUser> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(RegularUser::getId)).hasSize(1).contains(1);
  }

  @FlywayTest
  @Test
  void shouldReturnAllForSearchWithNoParams() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search").build().encode().toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<RegularUser>>() {});
    List<RegularUser> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(1);
  }

  @FlywayTest
  @Test
  void shouldDeleteEntityUnderGivenId() {
    restTemplate.delete(RESOURCE_LINK + "/1");

    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", String.class);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldUpdateExistingEntityCorrectly() {
    ResponseEntity<RegularUser> initialResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", RegularUser.class);

    RegularUser existingResourceDto = initialResponse.getBody();
    existingResourceDto.setCity("new city");
    existingResourceDto.setPhone("123456789");
    existingResourceDto.getPerson().setFirstName("Jan Janowicz");

    restTemplate.put(RESOURCE_LINK + "/1", existingResourceDto);

    ResponseEntity<RegularUser> afterUpdateResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", RegularUser.class);

    RegularUser updatedResourceDto = afterUpdateResponse.getBody();

    assertEquals(HttpStatus.OK, afterUpdateResponse.getStatusCode());
    assertEquals(existingResourceDto.getId(), updatedResourceDto.getId());
    assertEquals("new city", updatedResourceDto.getCity());
    assertEquals("123456789", updatedResourceDto.getPhone());
    assertEquals("Jan Janowicz", updatedResourceDto.getPerson().getFirstName());
  }

  @FlywayTest
  @Test
  void shouldReturnAllExistingResources() throws IOException {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(RESOURCE_LINK, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<RegularUser>>() {});
    List<RegularUser> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(RegularUser::getId))
        .hasSize(1)
        .allMatch(Objects::nonNull);
  }

  @FlywayTest
  @Test
  void shouldReturnDtoUnderExistingId() {
    ResponseEntity<RegularUser> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", RegularUser.class);
    RegularUser responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Adam", responseDto.getPerson().getFirstName());
    assertEquals("Wrocław", responseDto.getCity());
    assertEquals(1, responseDto.getId());
  }

  @FlywayTest
  @Test
  void shouldCreateRegularUserAndAssignItToCredentialsForCorrectCredentialsWithoutCreatedUser() {
    Voivodeship voivodeship = new Voivodeship();
    voivodeship.setId(10);
    Person person = new Person();
    person.setFirstName("Jan");
    person.setLastName("Kowalski");
    person.setGender("M");
    person.setBirthdate(LocalDate.of(1987, 2, 3));

    RegularUser newRegularUser = new RegularUser();
    newRegularUser.setPerson(person);
    newRegularUser.setCity("city");
    newRegularUser.setVoivodeship(voivodeship);
    newRegularUser.setLinkName("superuser333");
    newRegularUser.setPhone("123123123");

    HttpEntity<RegularUser> regularUserRequest =
        new HttpEntity<>(newRegularUser, jwtHeaderForConfirmedCredentialsWithoutCreatedUser);

    ResponseEntity<RegularUser> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, regularUserRequest, RegularUser.class);

    RegularUser createdRegularUser = responseEntity.getBody();
    Credentials updatedCredentials = credentialsService.findById(12).orElseThrow();

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertNotNull(createdRegularUser);
    assertNotNull(createdRegularUser.getId());
    assertEquals("Jan", createdRegularUser.getPerson().getFirstName());
    assertEquals("Kowalski", createdRegularUser.getPerson().getLastName());
    assertEquals(LocalDate.of(1987, 2, 3), createdRegularUser.getPerson().getBirthdate());
    assertNotNull(createdRegularUser.getPerson().getId());
    assertEquals("city", createdRegularUser.getCity());
    assertEquals(10, createdRegularUser.getVoivodeship().getId());
    assertEquals("superuser333", newRegularUser.getLinkName());
    assertEquals("123123123", newRegularUser.getPhone());
    assertEquals("confirmed@nouser.com", updatedCredentials.getEmail());
    assertTrue(passwordEncoder.matches("passpass56", updatedCredentials.getPassword()));
    assertEquals(
        UserAuthority.ROLE_REGULAR_USER, updatedCredentials.getAuthority().getUserAuthority());
    assertEquals(createdRegularUser.getId(), updatedCredentials.getUserId());
  }
}
