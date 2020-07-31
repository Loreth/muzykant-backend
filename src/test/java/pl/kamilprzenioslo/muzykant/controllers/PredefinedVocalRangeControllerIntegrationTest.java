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
import pl.kamilprzenioslo.muzykant.dtos.PredefinedVocalRange;

@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PredefinedVocalRangeControllerIntegrationTest {
  @Autowired private TestRestTemplate restTemplate;
  @Autowired private ObjectMapper objectMapper;
  private final String RESOURCE_LINK;

  public PredefinedVocalRangeControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/predefined-vocal-ranges";
  }

  @FlywayTest
  @Test
  void shouldReturnAllExistingResourcesPagedProperly() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK).build().encode().toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<PredefinedVocalRange>>() {});
    List<PredefinedVocalRange> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertEquals(1, jsonResponseBody.get("totalPages").intValue());
    assertThat(responseAdList.stream().map(PredefinedVocalRange::getId)).hasSize(6).allMatch(Objects::nonNull);
  }

  @FlywayTest
  @Test
  void shouldReturnDtoUnderExistingId() {
    ResponseEntity<PredefinedVocalRange> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/2", PredefinedVocalRange.class);
    PredefinedVocalRange responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("mezzosopran", responseDto.getName());
    assertEquals(2, responseDto.getId());
  }

  @FlywayTest
  @Test
  void shouldNotReturnDtoUnderNotExistingId() {
    ResponseEntity<PredefinedVocalRange> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/123", PredefinedVocalRange.class);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }
}
