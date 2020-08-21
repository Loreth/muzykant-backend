package pl.kamilprzenioslo.muzykant.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kamilprzenioslo.muzykant.dtos.Person;
import pl.kamilprzenioslo.muzykant.dtos.RegularUser;
import pl.kamilprzenioslo.muzykant.dtos.Voivodeship;

@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RegularUserControllerIntegrationTest {
  @Autowired private TestRestTemplate restTemplate;
  @Autowired private ObjectMapper objectMapper;
  private final String RESOURCE_LINK;

  public RegularUserControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/regular-users";
  }

  @FlywayTest
  @Test
  void shouldReturnAllWithGivenParameters() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("name", "ADI")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<RegularUser>>() {});
    List<RegularUser> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(RegularUser::getId)).hasSize(1).contains(1);
  }

  @FlywayTest
  @Test
  void shouldReturnAllForSearchWithNoParams() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search").build().encode().toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<RegularUser>>() {});
    List<RegularUser> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(1);
  }

  @FlywayTest
  @Test
  void shouldCreateEntityAndReturnDtoWithId() {
    RegularUser requestDto = new RegularUser();
    Voivodeship voivodeship = new Voivodeship();
    voivodeship.setId(5);
    Person person = new Person();
    person.setFirstName("Janina");
    person.setLastName("Kowalska");
    person.setGender("K");
    person.setBirthdate(LocalDate.of(1980, 2, 3));

    requestDto.setPerson(person);
    requestDto.setCity("city");
    requestDto.setVoivodeship(voivodeship);
    requestDto.setLinkName("zwyczajny");

    ResponseEntity<RegularUser> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, requestDto, RegularUser.class);
    RegularUser responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals("Janina", responseDto.getPerson().getFirstName());
    assertEquals("Kowalska", responseDto.getPerson().getLastName());
    assertEquals(LocalDate.of(1980, 2, 3), responseDto.getPerson().getBirthdate());
    assertNotNull(responseDto.getPerson().getId());
    assertEquals("K", responseDto.getPerson().getGender());
    assertEquals("city", responseDto.getCity());
    assertEquals(5, responseDto.getVoivodeship().getId());
    assertEquals("zwyczajny", requestDto.getLinkName());
  }

  @FlywayTest
  @Test
  void shouldDeleteEntityUnderGivenId() {
    restTemplate.delete(RESOURCE_LINK + "/1");

    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", String.class);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldUpdateExistingEntityCorrectly() {
    ResponseEntity<RegularUser> initialResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", RegularUser.class);

    RegularUser existingResourceDto = initialResponse.getBody();
    existingResourceDto.setCity("new city");
    existingResourceDto.setPhone("123456789");
    existingResourceDto.getPerson().setFirstName("Jan Janowicz");

    restTemplate.put(RESOURCE_LINK + "/1", existingResourceDto);

    ResponseEntity<RegularUser> afterUpdateResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", RegularUser.class);

    RegularUser updatedResourceDto = afterUpdateResponse.getBody();

    assertEquals(HttpStatus.OK, afterUpdateResponse.getStatusCode());
    assertEquals(existingResourceDto.getId(), updatedResourceDto.getId());
    assertEquals("new city", updatedResourceDto.getCity());
    assertEquals("123456789", updatedResourceDto.getPhone());
    assertEquals("Jan Janowicz", updatedResourceDto.getPerson().getFirstName());
  }

  @FlywayTest
  @Test
  void shouldReturnAllExistingResources() throws IOException {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(RESOURCE_LINK, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<RegularUser>>() {});
    List<RegularUser> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList.stream().map(RegularUser::getId))
        .hasSize(1)
        .allMatch(Objects::nonNull);
  }

  @FlywayTest
  @Test
  void shouldReturnDtoUnderExistingId() {
    ResponseEntity<RegularUser> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", RegularUser.class);
    RegularUser responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Adam", responseDto.getPerson().getFirstName());
    assertEquals("Wrocław", responseDto.getCity());
    assertEquals(1, responseDto.getId());
  }
}
