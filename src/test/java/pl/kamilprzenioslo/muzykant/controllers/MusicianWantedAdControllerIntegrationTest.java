package pl.kamilprzenioslo.muzykant.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import java.util.Set;
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
import org.springframework.web.util.UriComponentsBuilder;
import pl.kamilprzenioslo.muzykant.config.TestSecurityConfiguration;
import pl.kamilprzenioslo.muzykant.dtos.Ad;
import pl.kamilprzenioslo.muzykant.dtos.Genre;
import pl.kamilprzenioslo.muzykant.dtos.MusicianWantedAd;
import pl.kamilprzenioslo.muzykant.dtos.VocalRange;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserType;

@Import(TestSecurityConfiguration.class)
@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MusicianWantedAdControllerIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private HttpHeaders jwtHeaderForUserWithMusicianWantedAd;
  private final String RESOURCE_LINK;

  public MusicianWantedAdControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/musician-wanted-ads";
  }

  @FlywayTest
  @Test
  void shouldReturnAllWithGivenParameters() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("publishedDateAfterInclusive", "2020-07-03")
            .queryParam("publishedDateBeforeInclusive", "2020-07-19")
            .queryParam("minAge", 20)
            .queryParam("maxAge", 123)
            .queryParam("location", "Zamość")
            .queryParam("preferredGenreIds", "22,12,7")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader =
        objectMapper.readerFor(new TypeReference<List<MusicianWantedAd>>() {});
    List<MusicianWantedAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(MusicianWantedAd::getId)).hasSize(1).contains(5);
  }

  @FlywayTest
  @Test
  void shouldNotReturnAnyAdsForGivenGenres() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("preferredGenreIds", "24,13,50")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader =
        objectMapper.readerFor(new TypeReference<List<MusicianWantedAd>>() {});
    List<MusicianWantedAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).isEmpty();
  }

  @FlywayTest
  @Test
  void shouldReturnAllAdsThatHaveAtLeastOneOfGivenInstruments() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("preferredInstrumentIds", "1,2,3")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader =
        objectMapper.readerFor(new TypeReference<List<MusicianWantedAd>>() {});
    List<MusicianWantedAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(3);
  }

  @FlywayTest
  @Test
  void shouldReturnAllAdsThatHaveAtLeastOneOfGivenLookingPreferredGenres() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("lookingPreferredGenreIds", "1,4,8,32")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader =
        objectMapper.readerFor(new TypeReference<List<MusicianWantedAd>>() {});
    List<MusicianWantedAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(Ad::getId)).containsExactlyInAnyOrder(3, 4);
  }

  @FlywayTest
  @Test
  void shouldReturnAllAdsThatHaveAtLeastOneOfGivenVoivodeships() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("voivodeshipIds", "1,3,12,4")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader =
        objectMapper.readerFor(new TypeReference<List<MusicianWantedAd>>() {});
    List<MusicianWantedAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(4);
  }

  @FlywayTest
  @Test
  void shouldReturnAllAdsForSearchWithNoParams() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search").build().encode().toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader =
        objectMapper.readerFor(new TypeReference<List<MusicianWantedAd>>() {});
    List<MusicianWantedAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(5);
  }

  @FlywayTest
  @Test
  void shouldCreateEntityAndReturnDtoWithIdWithProperAuthorization() {
    MusicianWantedAd requestDto = new MusicianWantedAd();
    Genre preferredGenre1 = new Genre();
    preferredGenre1.setId(1);
    Genre preferredGenre2 = new Genre();
    preferredGenre2.setId(2);
    requestDto.setLocation("location");
    requestDto.setPublishedDate(LocalDate.of(2020, 9, 11));
    requestDto.setPreferredGenres(Set.of(preferredGenre1, preferredGenre2));
    requestDto.setUserId(1);

    HttpEntity<MusicianWantedAd> requestEntity =
        new HttpEntity<>(requestDto, jwtHeaderForUserWithMusicianWantedAd);

    ResponseEntity<MusicianWantedAd> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, requestEntity, MusicianWantedAd.class);
    MusicianWantedAd responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals("location", responseDto.getLocation());
    assertEquals(LocalDate.of(2020, 9, 11), responseDto.getPublishedDate());
    assertEquals(Set.of(preferredGenre1, preferredGenre2), responseDto.getPreferredGenres());
  }

  @FlywayTest
  @Test
  void shouldDeleteEntityUnderGivenId() {
    HttpEntity<String> requestEntity = new HttpEntity<>(jwtHeaderForUserWithMusicianWantedAd);

    ResponseEntity<String> responseEntity =
        restTemplate.exchange(RESOURCE_LINK + "/5", HttpMethod.DELETE, requestEntity, String.class);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldUpdateExistingEntityCorrectly() {
    ResponseEntity<MusicianWantedAd> initialResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/5", MusicianWantedAd.class);

    MusicianWantedAd existingResourceDto = initialResponse.getBody();
    existingResourceDto.setLocation("new location");
    existingResourceDto.setVocalRange(new VocalRange("E3", "E5"));
    existingResourceDto.setCommercial(true);
    Genre newPreferredGenre = new Genre();
    newPreferredGenre.setId(25);
    existingResourceDto.getPreferredGenres().add(newPreferredGenre);

    HttpEntity<MusicianWantedAd> requestEntity =
        new HttpEntity<>(existingResourceDto, jwtHeaderForUserWithMusicianWantedAd);

    restTemplate.put(RESOURCE_LINK + "/5", requestEntity);

    ResponseEntity<MusicianWantedAd> afterUpdateResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/5", MusicianWantedAd.class);

    MusicianWantedAd updatedResourceDto = afterUpdateResponse.getBody();

    assertEquals(HttpStatus.OK, afterUpdateResponse.getStatusCode());
    assertEquals(existingResourceDto.getId(), updatedResourceDto.getId());
    assertEquals(existingResourceDto.getMaxAge(), updatedResourceDto.getMaxAge());
    assertEquals("new location", updatedResourceDto.getLocation());
    assertNotNull(updatedResourceDto.getVocalRange().getId());
    assertEquals("E3", updatedResourceDto.getVocalRange().getLowestNote());
    assertEquals("E5", updatedResourceDto.getVocalRange().getHighestNote());
    assertTrue(updatedResourceDto.isCommercial());
    assertThat(updatedResourceDto.getPreferredGenres().stream().map(Genre::getId))
        .hasSize(6)
        .contains(newPreferredGenre.getId());
  }

  @FlywayTest
  @Test
  void shouldReturnAllExistingResources() throws IOException {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(RESOURCE_LINK, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader =
        objectMapper.readerFor(new TypeReference<List<MusicianWantedAd>>() {});
    List<MusicianWantedAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(MusicianWantedAd::getId))
        .hasSize(5)
        .allMatch(Objects::nonNull);
  }

  @FlywayTest
  @Test
  void shouldReturnDtoUnderExistingId() {
    ResponseEntity<MusicianWantedAd> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/4", MusicianWantedAd.class);
    MusicianWantedAd responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals((short) 20, responseDto.getMinAge());
    assertEquals((short) 30, responseDto.getMaxAge());
    assertEquals("F", responseDto.getPreferredGender());
    assertEquals("Opis opis", responseDto.getDescription());
    assertEquals("Warszawa", responseDto.getLocation());
    assertEquals(LocalDate.parse("2020-08-13"), responseDto.getPublishedDate());
    assertEquals(3, responseDto.getUserId());
    assertEquals(UserType.MUSICIAN, responseDto.getUserType());
    assertEquals("Grajek", responseDto.getUserDisplayName());
    assertThat(responseDto.getUserGenres().stream().map(Genre::getId))
        .containsExactlyInAnyOrder(3, 11, 8);
    assertEquals(
        "http://localhost:8080/user-images/image-uploads/3_profile-image.jpg",
        responseDto.getUserProfileImageLink());
    assertFalse(responseDto.isCommercial());
  }
}
