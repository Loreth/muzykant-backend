package pl.kamilprzenioslo.muzykant.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
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
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kamilprzenioslo.muzykant.dtos.UserImage;

@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserUserImageControllerIntegrationTest {
  @Autowired private TestRestTemplate restTemplate;
  @Autowired private ObjectMapper objectMapper;
  private final String RESOURCE_LINK;

  public UserUserImageControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/user-images";
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
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<UserImage>>() {});
    List<UserImage> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

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
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<UserImage>>() {});
    List<UserImage> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseAdList).hasSize(3);
  }

  @FlywayTest
  @Test
  void shouldCreateEntityAndReturnDtoWithId() {
    UserImage requestDto = new UserImage();
    requestDto.setLink("link");
    requestDto.setUserId(6);

    ResponseEntity<UserImage> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, requestDto, UserImage.class);
    UserImage responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals("link", responseDto.getLink());
  }

  @FlywayTest
  @Test
  void shouldNotCreateEntityWithoutMusicianIdInRequest() {
    UserImage requestDto = new UserImage();
    requestDto.setLink("link");

    ResponseEntity<UserImage> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK, requestDto, UserImage.class);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldUpdateExistingEntityCorrectly() {
    ResponseEntity<UserImage> initialResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", UserImage.class);

    UserImage existingResourceDto = initialResponse.getBody();
    existingResourceDto.setLink("new Image link");

    restTemplate.put(RESOURCE_LINK + "/1", existingResourceDto);

    ResponseEntity<UserImage> afterUpdateResponse =
        restTemplate.getForEntity(RESOURCE_LINK + "/1", UserImage.class);

    UserImage updatedResourceDto = afterUpdateResponse.getBody();

    assertEquals("new Image link", updatedResourceDto.getLink());
  }

  @FlywayTest
  @Test
  void shouldReturnAllExistingResourcesPagedProperly() throws IOException {
    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search").build().encode().toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<UserImage>>() {});
    List<UserImage> responseAdList = listReader.readValue(jsonResponseBody.get("content"));

    assertEquals(1, jsonResponseBody.get("totalPages").intValue());
    assertThat(responseAdList.stream().map(UserImage::getId)).hasSize(3).allMatch(Objects::nonNull);
  }

  @FlywayTest
  @Test
  void shouldReturnDtoUnderExistingId() {
    ResponseEntity<UserImage> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/2", UserImage.class);
    UserImage responseDto = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("placeholder", responseDto.getLink());
    assertEquals(2, responseDto.getUserId());
    assertEquals(2, responseDto.getId());
  }

  @FlywayTest
  @Test
  void shouldNotReturnDtoUnderNotExistingId() {
    ResponseEntity<UserImage> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/123", UserImage.class);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @FlywayTest
  @Test
  void shouldSaveUploadedImageInUploadDirectoryAndCreateUserImageEntity() throws IOException {
    MultipartFile imageFile =
        new MockMultipartFile("img123321", "filename.jpg", "image/jpeg", "mock img".getBytes());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", imageFile.getResource());
    body.add("userId", 3);
    body.add("orderIndex", 1);

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(RESOURCE_LINK + "/upload", requestEntity, String.class);

    String createdImageLink = responseEntity.getBody();

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(createdImageLink, RESOURCE_LINK + "/image-uploads/3_2.jpg");

    //cleanup
    Files.deleteIfExists(Path.of("./test-uploads/3_2.jpg"));
  }

  @FlywayTest
  @Test
  void downloadUserImage() {
    ResponseEntity<Resource> responseEntity =
        restTemplate.getForEntity(RESOURCE_LINK + "/image-uploads/3_1.jpg", Resource.class);

    Resource body = responseEntity.getBody();
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("3_1.jpg", body.getFilename());
  }
}
