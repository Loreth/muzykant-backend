//package pl.kamilprzenioslo.muzykant.controllers;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectReader;
//import java.io.IOException;
//import java.net.URI;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//import org.flywaydb.test.annotation.FlywayTest;
//import org.flywaydb.test.junit5.annotation.FlywayTestExtension;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.util.UriComponentsBuilder;
//import pl.kamilprzenioslo.muzykant.dtos.Genre;
//import pl.kamilprzenioslo.muzykant.dtos.Band;
//import pl.kamilprzenioslo.muzykant.dtos.VocalRange;
//
//@FlywayTestExtension
//@FlywayTest
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//class BandControllerIntegrationTest {
//  @Autowired private TestRestTemplate restTemplate;
//  @Autowired private ObjectMapper objectMapper;
//  private final String RESOURCE_LINK;
//
//  public BandControllerIntegrationTest(@LocalServerPort int port) {
//    RESOURCE_LINK = "http://localhost:" + port + "/bands";
//  }
//
//  @FlywayTest
//  @Test
//  void shouldReturnAllWithGivenParameters() throws IOException {
//    URI requestUri =
//        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
//            .queryParam("publishedDateAfterInclusive", "2020-07-03")
//            .queryParam("publishedDateBeforeInclusive", "2020-07-19")
//            .queryParam("minAge", 20)
//            .queryParam("maxAge", 123)
//            .queryParam("location", "Wrocław,Zamość,Warszawa")
//            .queryParam("preferredGenreIds", "22,12,7")
//            .build()
//            .encode()
//            .toUri();
//
//    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);
//
//    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
//    ObjectReader listReader =
//        objectMapper.readerFor(new TypeReference<List<Band>>() {});
//    List<Band> responseAdList = listReader.readValue(jsonResponseBody.get("content"));
//
//    assertThat(responseAdList.stream().map(Band::getId)).hasSize(1).contains(5);
//  }
//
//  @FlywayTest
//  @Test
//  void shouldNotReturnAnyAdsForGivenGenres() throws IOException {
//    URI requestUri =
//        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
//            .queryParam("preferredGenreIds", "22,12,7,50")
//            .build()
//            .encode()
//            .toUri();
//
//    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);
//
//    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
//    ObjectReader listReader =
//        objectMapper.readerFor(new TypeReference<List<Band>>() {});
//    List<Band> responseAdList = listReader.readValue(jsonResponseBody.get("content"));
//
//    assertThat(responseAdList).isEmpty();
//  }
//
//  @FlywayTest
//  @Test
//  void shouldReturnAllAdsThatHaveAtLeastAllOfGivenInstruments() throws IOException {
//    URI requestUri =
//        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
//            .queryParam("preferredInstrumentIds", "1,2,3")
//            .build()
//            .encode()
//            .toUri();
//
//    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);
//
//    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
//    ObjectReader listReader =
//        objectMapper.readerFor(new TypeReference<List<Band>>() {});
//    List<Band> responseAdList = listReader.readValue(jsonResponseBody.get("content"));
//
//    assertThat(responseAdList).hasSize(2);
//  }
//
//  @FlywayTest
//  @Test
//  void shouldReturnAllAdsForSearchWithNoParams() throws IOException {
//    URI requestUri =
//        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search").build().encode().toUri();
//
//    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);
//
//    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
//    ObjectReader listReader =
//        objectMapper.readerFor(new TypeReference<List<Band>>() {});
//    List<Band> responseAdList = listReader.readValue(jsonResponseBody.get("content"));
//
//    assertThat(responseAdList).hasSize(5);
//  }
//
//  @FlywayTest
//  @Test
//  void shouldCreateEntityAndReturnDtoWithId() {
//    Band requestDto = new Band();
//    Genre preferredGenre1 = new Genre();
//    preferredGenre1.setId(1);
//    Genre preferredGenre2 = new Genre();
//    preferredGenre2.setId(2);
//    requestDto.setLocation("location");
//    requestDto.setPublishedDate(LocalDate.of(2020, 9, 11));
//    requestDto.setPreferredGenres(Set.of(preferredGenre1, preferredGenre2));
//    requestDto.setUserId(1);
//
//    ResponseEntity<Band> responseEntity =
//        restTemplate.postForEntity(RESOURCE_LINK, requestDto, Band.class);
//    Band responseDto = responseEntity.getBody();
//
//    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//    assertEquals("location", responseDto.getLocation());
//    assertEquals(LocalDate.of(2020, 9, 11), responseDto.getPublishedDate());
//    assertEquals(Set.of(preferredGenre1, preferredGenre2), responseDto.getPreferredGenres());
//  }
//
//  @FlywayTest
//  @Test
//  void shouldDeleteEntityUnderGivenId() {
//    restTemplate.delete(RESOURCE_LINK + "/4");
//
//    ResponseEntity<String> responseEntity =
//        restTemplate.getForEntity(RESOURCE_LINK + "/4", String.class);
//
//    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//  }
//
//  @FlywayTest
//  @Test
//  void shouldUpdateExistingEntityCorrectly() {
//    ResponseEntity<Band> initialResponse =
//        restTemplate.getForEntity(RESOURCE_LINK + "/4", Band.class);
//
//    Band existingResourceDto = initialResponse.getBody();
//    existingResourceDto.setLocation("new location");
//    existingResourceDto.setVocalRange(new VocalRange("E3", "E5"));
//    existingResourceDto.setCommercial(true);
//    Genre newPreferredGenre = new Genre();
//    newPreferredGenre.setId(25);
//    existingResourceDto.getPreferredGenres().add(newPreferredGenre);
//
//    restTemplate.put(RESOURCE_LINK + "/4", existingResourceDto);
//
//    ResponseEntity<Band> afterUpdateResponse =
//        restTemplate.getForEntity(RESOURCE_LINK + "/4", Band.class);
//
//    Band updatedResourceDto = afterUpdateResponse.getBody();
//
//    assertEquals(HttpStatus.OK, afterUpdateResponse.getStatusCode());
//    assertEquals(existingResourceDto.getId(), updatedResourceDto.getId());
//    assertEquals(existingResourceDto.getMaxAge(), updatedResourceDto.getMaxAge());
//    assertEquals("new location", updatedResourceDto.getLocation());
//    assertNotNull(updatedResourceDto.getVocalRange().getId());
//    assertEquals("E3", updatedResourceDto.getVocalRange().getLowestNote());
//    assertEquals("E5", updatedResourceDto.getVocalRange().getHighestNote());
//    assertTrue(updatedResourceDto.isCommercial());
//    assertThat(updatedResourceDto.getPreferredGenres().stream().map(Genre::getId))
//        .hasSize(3)
//        .contains(newPreferredGenre.getId());
//  }
//
//  @FlywayTest
//  @Test
//  void shouldReturnAllExistingResources() throws IOException {
//    ResponseEntity<String> responseEntity = restTemplate.getForEntity(RESOURCE_LINK, String.class);
//
//    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
//    ObjectReader listReader =
//        objectMapper.readerFor(new TypeReference<List<Band>>() {});
//    List<Band> responseAdList = listReader.readValue(jsonResponseBody.get("content"));
//
//    assertThat(responseAdList.stream().map(Band::getId))
//        .hasSize(5)
//        .allMatch(Objects::nonNull);
//  }
//
//  @FlywayTest
//  @Test
//  void shouldReturnDtoUnderExistingId() {
//    ResponseEntity<Band> responseEntity =
//        restTemplate.getForEntity(RESOURCE_LINK + "/4", Band.class);
//    Band responseDto = responseEntity.getBody();
//
//    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    assertEquals((byte) 20, responseDto.getMinAge());
//    assertEquals((byte) 30, responseDto.getMaxAge());
//    assertEquals('K', responseDto.getPreferredGender());
//    assertEquals("Opis opis", responseDto.getDescription());
//    assertEquals("Warszawa", responseDto.getLocation());
//    assertEquals(LocalDate.parse("2020-08-13"), responseDto.getPublishedDate());
//    assertEquals(3, responseDto.getUserId());
//    assertFalse(responseDto.isCommercial());
//  }
//}
