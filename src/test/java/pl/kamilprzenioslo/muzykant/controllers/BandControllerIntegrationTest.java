package pl.kamilprzenioslo.muzykant.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kamilprzenioslo.muzykant.dtos.Band;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.dtos.Genre;
import pl.kamilprzenioslo.muzykant.dtos.Voivodeship;
import pl.kamilprzenioslo.muzykant.dtos.security.VerifiedEmailSignUpRequest;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;

@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BandControllerIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private CredentialsService credentialsService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  private final String RESOURCE_LINK;

  public BandControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/bands";
  }

  @FlywayTest
  @Test
  void shouldReturnAllWithGivenParameters() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("city", "Wrocław")
            .queryParam("genreIds", "26,10,11,22")
            .queryParam("name", "a")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Band>>() {});
    List<Band> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(Band::getId)).hasSize(2);
  }

  @FlywayTest
  @Test
  void shouldNotReturnAnyForGivenGenres() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("genreIds", "36,4")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Band>>() {});
    List<Band> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).isEmpty();
  }

  @FlywayTest
  @Test
  void shouldReturnAllThatHaveAtLeastAllOfGivenInstruments() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("instrumentIds", "15,24")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Band>>() {});
    List<Band> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(2);
  }

  @FlywayTest
  @Test
  void shouldReturnAllForSearchWithNoParams() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search").build().encode().toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Band>>() {});
    List<Band> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(3);
  }

  @FlywayTest
  @Test
  void shouldCreateEntityAndReturnDtoWithId() {
    Band requestDto = new Band();
    Genre genre1 = new Genre();
    genre1.setId(1);
    Genre genre2 = new Genre();
    genre2.setId(2);
    Voivodeship voivodeship = new Voivodeship();
    voivodeship.setId(1);
    requestDto.setName("bandname");
    requestDto.setCity("city");
    requestDto.setVoivodeship(voivodeship);
    requestDto.setLinkName("linkname");
    requestDto.setPhone("123123123");
    requestDto.setGenres(Set.of(genre1, genre2));
    requestDto.setDescription("desc desc desc");

    ResponseEntity<Band> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, requestDto, Band.class);
    Band responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals("bandname", responseDto.getName());
    assertEquals("city", responseDto.getCity());
    assertEquals(1, responseDto.getVoivodeship().getId());
    assertEquals("linkname", requestDto.getLinkName());
    assertEquals("123123123", requestDto.getPhone());
    assertEquals(Set.of(genre1, genre2), responseDto.getGenres());
    assertEquals("desc desc desc", responseDto.getDescription());
  }

  @FlywayTest
  @Test
  void shouldDeleteEntityUnderGivenId() {
    restTemplate.delete(RESOURCE_LINK + "/8");

    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/8", String.class);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldUpdateExistingEntityCorrectly() {
    ResponseEntity<Band> initialResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/6", Band.class);

    Band existingResourceDto = initialResponse.getBody();
    existingResourceDto.setCity("new city");
    existingResourceDto.setPhone("new phone 123456789");
    Genre newGenre = new Genre();
    newGenre.setId(10);
    existingResourceDto.getGenres().add(newGenre);

    restTemplate.put(RESOURCE_LINK + "/6", existingResourceDto);

    ResponseEntity<Band> afterUpdateResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/6", Band.class);

    Band updatedResourceDto = afterUpdateResponse.getBody();

    assertEquals(HttpStatus.OK, afterUpdateResponse.getStatusCode());
    assertEquals(existingResourceDto.getId(), updatedResourceDto.getId());
    assertEquals("new city", updatedResourceDto.getCity());

    assertThat(updatedResourceDto.getGenres().stream().map(Genre::getId))
        .hasSize(5)
        .contains(newGenre.getId());
  }

  @FlywayTest
  @Test
  void shouldReturnAllExistingResources() throws IOException {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(RESOURCE_LINK, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Band>>() {});
    List<Band> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(Band::getId)).hasSize(3).allMatch(Objects::nonNull);
  }

  @FlywayTest
  @Test
  void shouldReturnDtoUnderExistingId() {
    ResponseEntity<Band> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/7", Band.class);
    Band responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Szakalaka", responseDto.getName());
    assertEquals("Wrocław", responseDto.getCity());
    assertEquals(7, responseDto.getId());
    assertEquals((short) 2020, responseDto.getFormationYear());
  }

  @FlywayTest
  @Test
  void shouldSignUpCorrectlyAndSaveCredentialsAndBandToRepository() {
    Voivodeship voivodeship = new Voivodeship();
    voivodeship.setId(10);

    Band newBand = new Band();
    newBand.setName("Cool band");
    newBand.setCity("city");
    newBand.setVoivodeship(voivodeship);
    newBand.setLinkName("superuser333");
    newBand.setPhone("123123123");

    VerifiedEmailSignUpRequest<Band> verifiedEmailSignUpRequest =
        new VerifiedEmailSignUpRequest<>("email@gmail.com", newBand);

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(
            RESOURCE_LINK + "/sign-up", verifiedEmailSignUpRequest, String.class);

    ResponseEntity<Band> createdBandResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/9", Band.class);
    Band createdBand = createdBandResponse.getBody();
    Credentials createdCredentials = credentialsService.findById(9).orElseThrow();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Cool band", createdBand.getName());
    assertEquals("city", createdBand.getCity());
    assertEquals(10, createdBand.getVoivodeship().getId());
    assertEquals("superuser333", newBand.getLinkName());
    assertEquals("123123123", newBand.getPhone());
    assertEquals("email@gmail.com", createdCredentials.getEmail());
    assertTrue(passwordEncoder.matches("mocnehaslo123", createdCredentials.getPassword()));
    assertEquals(UserAuthority.ROLE_BAND, createdCredentials.getAuthority().getUserAuthority());
    assertEquals(9, createdCredentials.getUserId());
  }
}
