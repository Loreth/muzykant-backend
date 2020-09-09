package pl.kamilprzenioslo.muzykant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import pl.kamilprzenioslo.muzykant.service.implementations.storage.StorageProperties;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(StorageProperties.class)
public class MuzykantApplication {

  public static void main(String[] args) {
    SpringApplication.run(MuzykantApplication.class, args);
  }
}
