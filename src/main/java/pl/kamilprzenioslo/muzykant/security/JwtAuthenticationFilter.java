package pl.kamilprzenioslo.muzykant.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.dtos.security.LoginRequest;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtUtils jwtUtils;
  private final ObjectMapper objectMapper;
  private final CredentialsService credentialsService;
  private final AuthenticationManager authenticationManager;
  private Credentials credentials;

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) {
    try {
      LoginRequest loginRequest =
          objectMapper.readValue(request.getInputStream(), LoginRequest.class);
      credentials = credentialsService.findByEmail(loginRequest.getUsername());

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsername(),
              loginRequest.getPassword(),
              List.of(credentials.getAuthority())));
    } catch (IOException e) {
      throw new InternalAuthenticationServiceException(e.getMessage());
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException {
    Claims claims =
        Jwts.claims(
            Map.of(
                "authority",
                credentials.getAuthority().getAuthority(),
                "userId",
                credentials.getUserId(),
                "linkName",
                credentials.getLinkName()));
    String token = jwtUtils.generateToken(authResult, claims);
    response.setContentType(MediaType.APPLICATION_JSON.toString());
    response.getWriter().print("{\"token\": \"" + token + "\"}");
  }
}
