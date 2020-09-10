package pl.kamilprzenioslo.muzykant.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Slf4j
@AllArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException {
    log.debug("Authentication failure: {}", exception.getMessage());
    Map<String, Object> data = new HashMap<>();
    data.put("status", HttpStatus.UNAUTHORIZED.value());
    data.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
    data.put("timestamp", Calendar.getInstance().getTime());
    data.put("exception", exception);
    data.put("message", exception.getMessage());
    response.setStatus(HttpStatus.UNAUTHORIZED.value());

    response.getOutputStream().println(objectMapper.writeValueAsString(data));
  }
}
