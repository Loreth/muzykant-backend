package pl.kamilprzenioslo.muzykant.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.net.URI;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kamilprzenioslo.muzykant.dtos.Genre;

@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GenreControllerIntegrationTest {
  @Autowired private TestRestTemplate restTemplate;
  @Autowired private ObjectMapper objectMapper;
  private final String RESOURCE_LINK;

  public GenreControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/genres";
  }

  @FlywayTest
  @Test
  void shouldReturnAllWithGivenParameters() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("name", "meTaL")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Genre>>() {});
    List<Genre> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(4);
  }

  @FlywayTest
  @Test
  void shouldReturnAllForSearchWithBigEnoughPageSize() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("size", 2000)
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Genre>>() {});
    List<Genre> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(38);
  }

  @FlywayTest
  @Test
  void shouldNotCreateEntityWithoutAuthorization() {
    Genre requestDto = new Genre();
    requestDto.setName("genre");

    ResponseEntity<Genre> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, requestDto, Genre.class);

    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldNotUpdateExistingEntityWithoutAuthorization() {
    ResponseEntity<Genre> initialResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/20", Genre.class);

    Genre existingResourceDto = initialResponse.getBody();
    String originalName = existingResourceDto.getName();
    existingResourceDto.setName("new genre name");

    ResponseEntity<Genre> putRequest =
        restTemplate.exchange(
            RESOURCE_LINK + "/20",
            HttpMethod.PUT,
            new HttpEntity<>(existingResourceDto),
            Genre.class);

    ResponseEntity<Genre> afterUpdateResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/20", Genre.class);

    Genre updatedResourceDto = afterUpdateResponse.getBody();

    assertEquals(HttpStatus.UNAUTHORIZED, putRequest.getStatusCode());
    assertEquals(originalName, updatedResourceDto.getName());
  }

  @FlywayTest
  @Test
  void shouldReturnAllExistingResourcesPagedProperly() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("size", 20)
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Genre>>() {});
    List<Genre> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertEquals(2, jsonResponseBody.get("totalPages").intValue());
    assertThat(responseAdList.stream().map(Genre::getId)).hasSize(20).allMatch(Objects::nonNull);
  }

  @FlywayTest
  @Test
  void shouldReturnDtoUnderExistingId() {
    ResponseEntity<Genre> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/27", Genre.class);
    Genre responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("New Wave", responseDto.getName());
    assertEquals(27, responseDto.getId());
  }
}
