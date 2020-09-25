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
import pl.kamilprzenioslo.muzykant.dtos.Genre;
import pl.kamilprzenioslo.muzykant.dtos.Instrument;
import pl.kamilprzenioslo.muzykant.dtos.JamSessionAd;

@Import(TestSecurityConfiguration.class)
@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class JamSessionAdControllerIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private HttpHeaders jwtHeaderForUserWithJamSessionAd;
  private final String RESOURCE_LINK;

  public JamSessionAdControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/jam-session-ads";
  }

  @FlywayTest
  @Test
  void shouldReturnAllWithGivenParameters() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("publishedDateAfterInclusive", "2020-01-23")
            .queryParam("publishedDateBeforeInclusive", "2020-07-23")
            .queryParam("location", "Wro")
            .queryParam("commercial", false)
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<JamSessionAd>>() {});
    List<JamSessionAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(JamSessionAd::getId)).hasSize(1);
  }

  @FlywayTest
  @Test
  void shouldNotReturnAnyAdsForGivenGenres() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("preferredGenreIds", "1,2")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<JamSessionAd>>() {});
    List<JamSessionAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).isEmpty();
  }

  @FlywayTest
  @Test
  void shouldReturnAllAdsThatHaveAtLeastAllOfGivenInstruments() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("preferredInstrumentIds", "7")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<JamSessionAd>>() {});
    List<JamSessionAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(1);
  }

  @FlywayTest
  @Test
  void shouldReturnAllAdsForSearchWithNoParams() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search").build().encode().toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<JamSessionAd>>() {});
    List<JamSessionAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(3);
  }

  @FlywayTest
  @Test
  void shouldCreateEntityAndReturnDtoWithIdWithProperAuthorization() {
    JamSessionAd requestDto = new JamSessionAd();
    Genre preferredGenre1 = new Genre();
    preferredGenre1.setId(10);
    Genre preferredGenre2 = new Genre();
    preferredGenre2.setId(1);
    Instrument preferredInstrument1 = new Instrument();
    preferredInstrument1.setId(23);
    Instrument preferredInstrument2 = new Instrument();
    preferredInstrument2.setId(4);
    requestDto.setLocation("location, somewhere 123");
    requestDto.setPublishedDate(LocalDate.of(2019, 12, 31));
    requestDto.setPreferredGenres(Set.of(preferredGenre1, preferredGenre2));
    requestDto.setPreferredInstruments(Set.of(preferredInstrument1, preferredInstrument2));
    requestDto.setUserId(6);

    HttpEntity<JamSessionAd> requestEntity =
        new HttpEntity<>(requestDto, jwtHeaderForUserWithJamSessionAd);

    ResponseEntity<JamSessionAd> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, requestEntity, JamSessionAd.class);
    JamSessionAd responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals("location, somewhere 123", responseDto.getLocation());
    assertEquals(LocalDate.of(2019, 12, 31), responseDto.getPublishedDate());
    assertEquals(Set.of(preferredGenre1, preferredGenre2), responseDto.getPreferredGenres());
    assertEquals(
        Set.of(preferredInstrument1, preferredInstrument2), responseDto.getPreferredInstruments());
    assertEquals(6, responseDto.getUserId());
  }

  @FlywayTest
  @Test
  void shouldDeleteEntityUnderGivenIdWithProperAuthorization() {
    HttpEntity<String> requestEntity = new HttpEntity<>(jwtHeaderForUserWithJamSessionAd);

    ResponseEntity<String> responseEntity =
        restTemplate.exchange(RESOURCE_LINK + "/9", HttpMethod.DELETE, requestEntity, String.class);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldUpdateExistingEntityCorrectly() {
    ResponseEntity<JamSessionAd> initialResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/9", JamSessionAd.class);

    JamSessionAd existingResourceDto = initialResponse.getBody();
    existingResourceDto.setLocation("new location");
    existingResourceDto.setCommercial(true);

    HttpEntity<JamSessionAd> requestEntity =
        new HttpEntity<>(existingResourceDto, jwtHeaderForUserWithJamSessionAd);

    restTemplate.put(RESOURCE_LINK + "/9", requestEntity);

    ResponseEntity<JamSessionAd> afterUpdateResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/9", JamSessionAd.class);

    JamSessionAd updatedResourceDto = afterUpdateResponse.getBody();

    assertEquals(HttpStatus.OK, afterUpdateResponse.getStatusCode());
    assertEquals(existingResourceDto.getId(), updatedResourceDto.getId());
    assertEquals("new location", updatedResourceDto.getLocation());
    assertTrue(updatedResourceDto.isCommercial());
  }

  @FlywayTest
  @Test
  void shouldReturnAllExistingResources() throws IOException {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(RESOURCE_LINK, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<JamSessionAd>>() {});
    List<JamSessionAd> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(JamSessionAd::getId))
        .hasSize(3)
        .allMatch(Objects::nonNull);
  }

  @FlywayTest
  @Test
  void shouldReturnDtoUnderExistingId() {
    ResponseEntity<JamSessionAd> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/9", JamSessionAd.class);
    JamSessionAd responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertNotNull(responseDto.getDescription());
    assertEquals("Radwanice/Wrocław", responseDto.getLocation());
    assertEquals(LocalDate.parse("2020-07-23"), responseDto.getPublishedDate());
    assertEquals(3, responseDto.getUserId());
    assertFalse(responseDto.isCommercial());
  }
}
