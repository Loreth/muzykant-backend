package pl.kamilprzenioslo.muzykant.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {

  private @Value("${app.storage.cloudinary.url}") String cloudinaryUrl;
  private @Value("${app.storage.cloudinary.image-dir}") String cloudinaryImgDir;

  @Bean
  public Cloudinary cloudinary() {
    return new Cloudinary(cloudinaryUrl);
  }

  @Bean("imageDownloadUri")
  public String prodImageDownloadUri() {
    return cloudinary().url().generate(cloudinaryImgDir);
  }
}
