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
import pl.kamilprzenioslo.muzykant.dtos.Equipment;

@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EquipmentControllerIntegrationTest {
  @Autowired private TestRestTemplate restTemplate;
  @Autowired private ObjectMapper objectMapper;
  private final String RESOURCE_LINK;

  public EquipmentControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/equipments";
  }

  @FlywayTest
  @Test
  void shouldReturnAllWithGivenParameters() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("musicianId", 2)
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Equipment>>() {});
    List<Equipment> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(3);
  }

  @FlywayTest
  @Test
  void shouldReturnAllForSearchWithBigEnoughPageSize() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("size", 5)
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Equipment>>() {});
    List<Equipment> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(4);
  }

  @FlywayTest
  @Test
  void shouldCreateEntityAndReturnDtoWithId() {
    Equipment requestDto = new Equipment();
    requestDto.setName("Equipment");
    requestDto.setMusicianId(2);

    ResponseEntity<Equipment> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, requestDto, Equipment.class);
    Equipment responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals("Equipment", responseDto.getName());
  }

  @FlywayTest
  @Test
  void shouldNotCreateEntityWithoutMusicianIdInRequest() {
    Equipment requestDto = new Equipment();
    requestDto.setName("Equipment");

    ResponseEntity<Equipment> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, requestDto, Equipment.class);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldUpdateExistingEntityCorrectly() {
    ResponseEntity<Equipment> initialResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/3", Equipment.class);

    Equipment existingResourceDto = initialResponse.getBody();
    existingResourceDto.setName("new Equipment name");

    restTemplate.put(RESOURCE_LINK + "/3", existingResourceDto);

    ResponseEntity<Equipment> afterUpdateResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/3", Equipment.class);

    Equipment updatedResourceDto = afterUpdateResponse.getBody();

    assertEquals("new Equipment name", updatedResourceDto.getName());
  }

  @FlywayTest
  @Test
  void shouldReturnAllExistingResourcesPagedProperly() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search").build().encode().toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<Equipment>>() {});
    List<Equipment> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertEquals(1, jsonResponseBody.get("totalPages").intValue());
    assertThat(responseAdList.stream().map(Equipment::getId)).hasSize(4).allMatch(Objects::nonNull);
  }

  @FlywayTest
  @Test
  void shouldReturnDtoUnderExistingId() {
    ResponseEntity<Equipment> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/2", Equipment.class);
    Equipment responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Vox AC30", responseDto.getName());
    assertEquals(2, responseDto.getId());
  }

  @FlywayTest
  @Test
  void shouldNotReturnDtoUnderNotExistingId() {
    ResponseEntity<Equipment> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/123", Equipment.class);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }
}
