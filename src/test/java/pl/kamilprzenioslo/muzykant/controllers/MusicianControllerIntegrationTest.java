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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kamilprzenioslo.muzykant.config.TestSecurityConfiguration;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.dtos.Instrument;
import pl.kamilprzenioslo.muzykant.dtos.Musician;
import pl.kamilprzenioslo.muzykant.dtos.Person;
import pl.kamilprzenioslo.muzykant.dtos.Voivodeship;
import pl.kamilprzenioslo.muzykant.security.UserAuthority;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;

@Import(TestSecurityConfiguration.class)
@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MusicianControllerIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private CredentialsService credentialsService;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private HttpHeaders jwtHeaderForConfirmedCredentialsWithoutCreatedUser;
  @Autowired private HttpHeaders jwtHeaderForMusicianWithImages;

  private final String RESOURCE_LINK;

  public MusicianControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/musicians";
  }

  @FlywayTest
  @Test
  void shouldReturnAllWithGivenParameters() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("genreIds", "3,25,1")
            .queryParam("instrumentIds", "46,45,44,43,1,2,3,4")
            .queryParam("name", "an")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Musician>>() {});
    List<Musician> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(Musician::getId)).hasSize(2);
  }

  @FlywayTest
  @Test
  void shouldNotReturnAnyForGivenGenres() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("genreIds", "20,26")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Musician>>() {});
    List<Musician> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).isEmpty();
  }

  @FlywayTest
  @Test
  void shouldReturnAllThatHaveAtLeastAllOfGivenInstruments() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("instrumentIds", "46")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Musician>>() {});
    List<Musician> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(3);
  }

  @FlywayTest
  @Test
  void shouldReturnAllForSearchWithNoParams() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search").build().encode().toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Musician>>() {});
    List<Musician> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(4);
  }

  @FlywayTest
  @Test
  void shouldDeleteEntityUnderGivenIdWithProperAuthentication() {
    HttpEntity<String> requestEntity = new HttpEntity<>(jwtHeaderForMusicianWithImages);

    ResponseEntity<String> responseEntity =
        restTemplate.exchange(RESOURCE_LINK + "/2", HttpMethod.DELETE, requestEntity, String.class);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldUpdateExistingEntityCorrectlyWithProperAuthentication() {
    ResponseEntity<Musician> initialResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/2", Musician.class);

    Musician existingResourceDto = initialResponse.getBody();
    existingResourceDto.setCity("new city");
    existingResourceDto.setPhone("new phone 123456789");
    Instrument newInstrument = new Instrument();
    newInstrument.setId(24);
    existingResourceDto.getInstruments().add(newInstrument);

    HttpEntity<Musician> requestEntity =
        new HttpEntity<>(existingResourceDto, jwtHeaderForMusicianWithImages);

    restTemplate.put(RESOURCE_LINK + "/2", requestEntity);

    ResponseEntity<Musician> afterUpdateResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/2", Musician.class);

    Musician updatedResourceDto = afterUpdateResponse.getBody();

    assertEquals(HttpStatus.OK, afterUpdateResponse.getStatusCode());
    assertEquals(existingResourceDto.getId(), updatedResourceDto.getId());
    assertEquals("new city", updatedResourceDto.getCity());

    assertThat(updatedResourceDto.getInstruments().stream().map(Instrument::getId))
        .hasSize(4)
        .contains(newInstrument.getId());
  }

  @FlywayTest
  @Test
  void shouldReturnAllExistingResources() throws IOException {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(RESOURCE_LINK, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Musician>>() {});
    List<Musician> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(Musician::getId)).hasSize(4).allMatch(Objects::nonNull);
  }

  @FlywayTest
  @Test
  void shouldReturnDtoUnderExistingId() {
    ResponseEntity<Musician> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/4", Musician.class);
    Musician responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("slash", responseDto.getLinkName());
    assertEquals("Katowice", responseDto.getCity());
    assertEquals(4, responseDto.getId());
  }

  @FlywayTest
  @Test
  void shouldCreateMusicianAndAssignItToCredentialsForCorrectCredentialsWithoutCreatedUser() {
    Voivodeship voivodeship = new Voivodeship();
    voivodeship.setId(10);
    Person person = new Person();
    person.setFirstName("Jan");
    person.setLastName("Kowalski");
    person.setGender("M");
    person.setBirthdate(LocalDate.of(1987, 2, 3));

    Musician newMusician = new Musician();
    newMusician.setPerson(person);
    newMusician.setCity("city");
    newMusician.setVoivodeship(voivodeship);
    newMusician.setLinkName("superuser333");
    newMusician.setPhone("123123123");

    HttpEntity<Musician> bandRequest =
        new HttpEntity<>(newMusician, jwtHeaderForConfirmedCredentialsWithoutCreatedUser);
    ResponseEntity<Musician> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, bandRequest, Musician.class);

    Musician createdMusician = responseEntity.getBody();
    Credentials updatedCredentials = credentialsService.findById(12).orElseThrow();

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals("Jan", createdMusician.getPerson().getFirstName());
    assertEquals("Kowalski", createdMusician.getPerson().getLastName());
    assertEquals(LocalDate.of(1987, 2, 3), createdMusician.getPerson().getBirthdate());
    assertNotNull(createdMusician.getPerson().getId());
    assertEquals("city", createdMusician.getCity());
    assertEquals(10, createdMusician.getVoivodeship().getId());
    assertEquals("superuser333", newMusician.getLinkName());
    assertEquals("123123123", newMusician.getPhone());
    assertEquals("confirmed@nouser.com", updatedCredentials.getEmail());
    assertTrue(passwordEncoder.matches("passpass56", updatedCredentials.getPassword()));
    assertEquals(UserAuthority.ROLE_MUSICIAN, updatedCredentials.getAuthority().getUserAuthority());
    assertEquals(createdMusician.getId(), updatedCredentials.getUserId());
  }
}
