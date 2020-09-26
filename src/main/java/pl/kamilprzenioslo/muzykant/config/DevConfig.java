package pl.kamilprzenioslo.muzykant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.kamilprzenioslo.muzykant.controllers.RestMappings;

@Configuration
@Profile("dev")
public class DevConfig {

  @Bean("imageDownloadUri")
  public String devImageDownloadUri() {
    return "http://localhost:8080" + RestMappings.USER_IMAGE + RestMappings.IMAGE_UPLOADS;
  }
}
