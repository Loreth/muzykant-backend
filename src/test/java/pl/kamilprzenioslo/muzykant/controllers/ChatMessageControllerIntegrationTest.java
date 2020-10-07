package pl.kamilprzenioslo.muzykant.controllers;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit5.annotation.FlywayTestExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.util.UriComponentsBuilder;
import pl.kamilprzenioslo.muzykant.config.TestSecurityConfiguration;
import pl.kamilprzenioslo.muzykant.dtos.ChatMessage;

@Import(TestSecurityConfiguration.class)
@FlywayTestExtension
@FlywayTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ChatMessageControllerIntegrationTest {
  @Autowired private TestRestTemplate restTemplate;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private HttpHeaders jwtHeaderForBand;
  @Autowired private HttpHeaders jwtHeaderForRegularUser;
  private WebSocketStompClient webSocketStompClient;
  private final String RESOURCE_LINK;
  private final String WEBSOCKET_ENDPOINT;

  public ChatMessageControllerIntegrationTest(@LocalServerPort int port) {
    RESOURCE_LINK = "http://localhost:" + port + "/chat-messages";
    WEBSOCKET_ENDPOINT = "ws://localhost:" + port + "/websocket";
  }

  @BeforeEach
  public void setup() {
    this.webSocketStompClient = new WebSocketStompClient(new StandardWebSocketClient());
  }

  @FlywayTest
  @Test
  void shouldConnectTwoUsersAndSecondOneShouldReceivePrivateMessageWhichShouldBePersisted()
      throws Exception {
    BlockingQueue<ChatMessage> messageQueue = new ArrayBlockingQueue<>(1);
    webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
    ChatMessage chatMessage = new ChatMessage("Hello adi", "zagubieni", "adi", null, false);
    StompHeaders bandConnectHeaders = new StompHeaders();
    bandConnectHeaders.addAll(jwtHeaderForBand);
    StompHeaders regularUserConnectHeaders = new StompHeaders();
    regularUserConnectHeaders.addAll(jwtHeaderForRegularUser);

    StompSession bandSession =
        webSocketStompClient
            .connect(
                WEBSOCKET_ENDPOINT,
                new WebSocketHttpHeaders(),
                bandConnectHeaders,
                new StompSessionHandlerAdapter() {})
            .get(1, SECONDS);

    StompSession regularUserSession =
        webSocketStompClient
            .connect(
                WEBSOCKET_ENDPOINT,
                new WebSocketHttpHeaders(),
                regularUserConnectHeaders,
                new StompSessionHandlerAdapter() {})
            .get(1, SECONDS);

    regularUserSession.subscribe(
        "/user/queue/chat",
        new StompFrameHandler() {

          @Override
          public Type getPayloadType(StompHeaders headers) {
            return ChatMessage.class;
          }

          @Override
          public void handleFrame(StompHeaders headers, Object payload) {
            System.out.println("Received message: " + payload);
            messageQueue.add((ChatMessage) payload);
          }
        });

    bandSession.send("/app/chat", chatMessage);

    assertEquals("Hello adi", messageQueue.poll(1, SECONDS).getContent());

    URI requestUri =
        UriComponentsBuilder.fromHttpUrl(RESOURCE_LINK + "/search")
            .queryParam("participantLinkName", "zagubieni")
            .queryParam("participantLinkName", "adi")
            .build()
            .encode()
            .toUri();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUri, String.class);

    JsonNode jsonResponseBody = objectMapper.readTree(responseEntity.getBody());
    ObjectReader listReader = objectMapper.readerFor(new TypeReference<List<ChatMessage>>() {});
    List<ChatMessage> responseChatMessageList =
        listReader.readValue(jsonResponseBody.get("content"));

    assertThat(responseChatMessageList).hasSize(1);
  }
}
