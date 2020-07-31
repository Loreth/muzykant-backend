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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kamilprzenioslo.muzykant.dtos.Image;

@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ImageControllerIntegrationTest {
  @Autowired private TestRestTemplate restTemplate;
  @Autowired private ObjectMapper objectMapper;
  private final String RESOURCE_LINK;

  public ImageControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/images";
  }

  @FlywayTest
  @Test
  void shouldReturnAllWithGivenParameters() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("userId", 2)
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Image>>() {});
    List<Image> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(2);
  }

  @FlywayTest
  @Test
  void shouldReturnAllForSearchWithBigEnoughPageSize() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("size", 3)
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Image>>() {});
    List<Image> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(3);
  }

  @FlywayTest
  @Test
  void shouldCreateEntityAndReturnDtoWithId() {
    Image requestDto = new Image();
    requestDto.setLink("link");
    requestDto.setUserId(6);

    ResponseEntity<Image> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, requestDto, Image.class);
    Image responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals("link", responseDto.getLink());
  }

  @FlywayTest
  @Test
  void shouldNotCreateEntityWithoutMusicianIdInRequest() {
    Image requestDto = new Image();
    requestDto.setLink("link");

    ResponseEntity<Image> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, requestDto, Image.class);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldUpdateExistingEntityCorrectly() {
    ResponseEntity<Image> initialResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", Image.class);

    Image existingResourceDto = initialResponse.getBody();
    existingResourceDto.setLink("new Image link");

    restTemplate.put(RESOURCE_LINK + "/1", existingResourceDto);

    ResponseEntity<Image> afterUpdateResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", Image.class);

    Image updatedResourceDto = afterUpdateResponse.getBody();

    assertEquals("new Image link", updatedResourceDto.getLink());
  }

  @FlywayTest
  @Test
  void shouldReturnAllExistingResourcesPagedProperly() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search").build().encode().toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Image>>() {});
    List<Image> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertEquals(1, jsonResponseBody.get("totalPages").intValue());
    assertThat(responseAdList.stream().map(Image::getId)).hasSize(3).allMatch(Objects::nonNull);
  }

  @FlywayTest
  @Test
  void shouldReturnDtoUnderExistingId() {
    ResponseEntity<Image> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/2", Image.class);
    Image responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("placeholder", responseDto.getLink());
    assertEquals(2, responseDto.getUserId());
    assertEquals(2, responseDto.getId());
  }

  @FlywayTest
  @Test
  void shouldNotReturnDtoUnderNotExistingId() {
    ResponseEntity<Image> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/123", Image.class);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }
}
