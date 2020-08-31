package pl.kamilprzenioslo.muzykant.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.dtos.security.LoginRequest;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtUtils jwtUtils;
  private final ObjectMapper objectMapper;
  private final AuthenticationManager authenticationManager;
  private final CredentialsService credentialsService;

  public JwtAuthenticationFilter(
      JwtUtils jwtUtils,
      ObjectMapper objectMapper,
      AuthenticationManager authenticationManager,
      CredentialsService credentialsService,
      AuthenticationFailureHandler authenticationFailureHandler) {
    this.jwtUtils = jwtUtils;
    this.objectMapper = objectMapper;
    this.authenticationManager = authenticationManager;
    this.credentialsService = credentialsService;
    this.setAuthenticationFailureHandler(authenticationFailureHandler);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) {
    try {
      LoginRequest loginRequest =
          objectMapper.readValue(request.getInputStream(), LoginRequest.class);

      UserDetails credentials = credentialsService.loadUserByUsername(loginRequest.getUsername());

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(credentials, loginRequest.getPassword()));
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
    Credentials principal = (Credentials) authResult.getPrincipal();

    String token = jwtUtils.generateToken(principal);
    response.setContentType(MediaType.APPLICATION_JSON.toString());
    response.getWriter().print("{\"token\": \"" + token + "\"}");
  }
}
