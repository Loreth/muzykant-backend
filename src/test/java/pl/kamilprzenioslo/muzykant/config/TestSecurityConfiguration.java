package pl.kamilprzenioslo.muzykant.config;

import static pl.kamilprzenioslo.muzykant.security.JwtConstants.JWT_PREFIX;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;
import pl.kamilprzenioslo.muzykant.security.JwtUtils;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;

@TestConfiguration
public class TestSecurityConfiguration {

  private final JwtUtils jwtUtils;
  private final CredentialsService credentialsService;

  public TestSecurityConfiguration(JwtUtils jwtUtils, CredentialsService credentialsService) {
    this.jwtUtils = jwtUtils;
    this.credentialsService = credentialsService;
  }

  @Bean
  MultiValueMap<String, String> jwtHeaderForConfirmedCredentialsWithoutCreatedUser() {
    Credentials credentials = credentialsService.findById(12).orElseThrow();
    LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add(HttpHeaders.AUTHORIZATION, JWT_PREFIX + jwtUtils.generateToken(credentials));

    return headers;
  }
}
