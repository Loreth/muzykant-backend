package pl.kamilprzenioslo.muzykant.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.kamilprzenioslo.muzykant.dtos.security.LoginRequest;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;

@SpringBootTest
@AutoConfigureMockMvc
class LoginEndpointTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  private final String LOGIN_URL = "/login";

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Test
  void shouldReturnCorrectJwtForCorrectCredentials() throws Exception {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("adam@gmail.com");
    loginRequest.setPassword("mocnehaslo123");
    String requestContent = objectMapper.writeValueAsString(loginRequest);

    MvcResult mvcResult =
        mockMvc
            .perform(
                post(LOGIN_URL).contentType(MediaType.APPLICATION_JSON).content(requestContent))
            .andExpect(status().isOk())
            .andReturn();

    HashMap<String, String> responseBody =
        objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
            });

    Jwts.parserBuilder()
        .requireSubject("adam@gmail.com")
        .require("authority", UserAuthority.ROLE_REGULAR_USER.name())
        .require("userId", 1)
        .require("linkName", "adi")
        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
        .build()
        .parse(responseBody.get("token"));
  }

  @Test
  void shouldReturn401StatusForIncorrectCredentials() throws Exception {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("adam@gmail.com");
    loginRequest.setPassword("wrongpassword");
    String requestContent = objectMapper.writeValueAsString(loginRequest);

    mockMvc
        .perform(post(LOGIN_URL).contentType(MediaType.APPLICATION_JSON).content(requestContent))
        .andExpect(status().isUnauthorized());
  }
}
