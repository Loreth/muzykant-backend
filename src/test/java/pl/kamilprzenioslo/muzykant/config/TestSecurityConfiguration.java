package pl.kamilprzenioslo.muzykant.config;

import static pl.kamilprzenioslo.muzykant.security.JwtConstants.JWT_PREFIX;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
  @Scope("prototype")
  HttpHeaders jwtHeaderForConfirmedCredentialsWithoutCreatedUser() {
    return makeJwtHeader(12);
  }

  @Bean
  @Scope("prototype")
  HttpHeaders jwtHeaderForMusicianWithImages() {
    return makeJwtHeader(2);
  }

  @Bean
  @Scope("prototype")
  HttpHeaders jwtHeaderForBand() {
    return makeJwtHeader(8);
  }

  @Bean
  @Scope("prototype")
  HttpHeaders jwtHeaderForRegularUser() {
    return makeJwtHeader(1);
  }

  @Bean
  @Scope("prototype")
  HttpHeaders jwtHeaderForUserWithBandWantedAd() {
    return makeJwtHeader(1);
  }

  @Bean
  @Scope("prototype")
  HttpHeaders jwtHeaderForUserWithMusicianWantedAd() {
    return makeJwtHeader(4);
  }

  @Bean
  @Scope("prototype")
  HttpHeaders jwtHeaderForUserWithJamSessionAd() {
    return makeJwtHeader(3);
  }

  private HttpHeaders makeJwtHeader(int credentialsId) {
    Credentials credentials = credentialsService.findById(credentialsId).orElseThrow();
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, JWT_PREFIX + jwtUtils.generateToken(credentials));
    headers.setContentType(MediaType.APPLICATION_JSON);

    return headers;
  }
}
