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
import pl.kamilprzenioslo.muzykant.dtos.BandWantedAd;
import pl.kamilprzenioslo.muzykant.dtos.Genre;
import pl.kamilprzenioslo.muzykant.dtos.Instrument;

@Import(TestSecurityConfiguration.class)
@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BandWantedAdControllerIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private HttpHeaders jwtHeaderForBand;
  @Autowired
  private HttpHeaders jwtHeaderForUserWithBandWantedAd;
  private final String RESOURCE_LINK;

  public BandWantedAdControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/band-wanted-ads";
  }

  @FlywayTest
  @Test
  void shouldReturnAllWithGivenParameters() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("publishedDateAfterInclusive", "2020-07-19")
            .queryParam("publishedDateBeforeInclusive", "2100-01-01")
            .queryParam("location", "Wrocław")
            .queryParam("commercial", false)
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<BandWantedAd>>() {});
    List<BandWantedAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(BandWantedAd::getId)).hasSize(1).contains(1);
  }

  @FlywayTest
  @Test
  void shouldNotReturnAnyAdsForGivenGenres() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("preferredGenreIds", "3,16,33")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<BandWantedAd>>() {});
    List<BandWantedAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).isEmpty();
  }

  @FlywayTest
  @Test
  void shouldReturnAllAdsThatHaveAtLeastAllOfGivenInstruments() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("preferredInstrumentIds", "34")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<BandWantedAd>>() {});
    List<BandWantedAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(1);
  }

  @FlywayTest
  @Test
  void shouldReturnAllAdsForSearchWithNoParams() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search").build().encode().toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<BandWantedAd>>() {});
    List<BandWantedAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(2);
  }

  @FlywayTest
  @Test
  void shouldCreateEntityAndReturnDtoWithIdWithProperAuthentication() {
    BandWantedAd requestDto = new BandWantedAd();
    Genre preferredGenre1 = new Genre();
    preferredGenre1.setId(10);
    Genre preferredGenre2 = new Genre();
    preferredGenre2.setId(20);
    Instrument preferredInstrument = new Instrument();
    preferredInstrument.setId(5);
    requestDto.setLocation("location");
    requestDto.setPublishedDate(LocalDate.of(2018, 1, 1));
    requestDto.setPreferredGenres(Set.of(preferredGenre1, preferredGenre2));
    requestDto.setPreferredInstruments(Set.of(preferredInstrument));
    requestDto.setUserId(2);

    HttpEntity<BandWantedAd> requestEntity = new HttpEntity<>(requestDto, jwtHeaderForBand);

    ResponseEntity<BandWantedAd> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, requestEntity, BandWantedAd.class);
    BandWantedAd responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals("location", responseDto.getLocation());
    assertEquals(LocalDate.of(2018, 1, 1), responseDto.getPublishedDate());
    assertEquals(Set.of(preferredGenre1, preferredGenre2), responseDto.getPreferredGenres());
    assertEquals(Set.of(preferredInstrument), responseDto.getPreferredInstruments());
    assertEquals(2, responseDto.getUserId());
  }

  @FlywayTest
  @Test
  void shouldDeleteEntityUnderGivenIdWithProperAuthorization() {
    HttpEntity<String> requestEntity = new HttpEntity<>(jwtHeaderForUserWithBandWantedAd);
    ResponseEntity<String> responseEntity =
        restTemplate.exchange(RESOURCE_LINK + "/1", HttpMethod.DELETE, requestEntity, String.class);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldUpdateExistingEntityCorrectlyWithProperAuthorization() {
    ResponseEntity<BandWantedAd> initialResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", BandWantedAd.class);

    BandWantedAd existingResourceDto = initialResponse.getBody();
    existingResourceDto.setLocation("new location");
    existingResourceDto.setCommercial(true);
    Genre newPreferredGenre1 = new Genre();
    newPreferredGenre1.setId(12); // already existent - should not add it
    Genre newPreferredGenre2 = new Genre();
    newPreferredGenre2.setId(33); // already existent - should not add it
    existingResourceDto.getPreferredGenres().add(newPreferredGenre1);
    existingResourceDto.getPreferredGenres().add(newPreferredGenre2);

    HttpEntity<BandWantedAd> requestEntity =
        new HttpEntity<>(existingResourceDto, jwtHeaderForUserWithBandWantedAd);

    restTemplate.put(RESOURCE_LINK + "/1", requestEntity);

    ResponseEntity<BandWantedAd> afterUpdateResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", BandWantedAd.class);

    BandWantedAd updatedResourceDto = afterUpdateResponse.getBody();

    assertEquals(HttpStatus.OK, afterUpdateResponse.getStatusCode());
    assertEquals(existingResourceDto.getId(), updatedResourceDto.getId());
    assertEquals("new location", updatedResourceDto.getLocation());
    assertTrue(updatedResourceDto.isCommercial());
    assertThat(updatedResourceDto.getPreferredGenres().stream().map(Genre::getId))
        .hasSize(3)
        .contains(newPreferredGenre2.getId());
  }

  @FlywayTest
  @Test
  void shouldReturnAllExistingResources() throws IOException {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(RESOURCE_LINK, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<BandWantedAd>>() {});
    List<BandWantedAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(BandWantedAd::getId))
        .hasSize(2)
        .allMatch(Objects::nonNull);
  }

  @FlywayTest
  @Test
  void shouldReturnDtoUnderExistingId() {
    ResponseEntity<BandWantedAd> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/2", BandWantedAd.class);
    BandWantedAd responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(null, responseDto.getDescription());
    assertEquals("Radwanice", responseDto.getLocation());
    assertEquals(LocalDate.parse("2020-08-10"), responseDto.getPublishedDate());
    assertEquals(1, responseDto.getUserId());
    assertTrue(responseDto.isCommercial());
  }
}
