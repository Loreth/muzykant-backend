package pl.kamilprzenioslo.muzykant.config;

import com.cloudinary.Cloudinary;
import java.util.List;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.kamilprzenioslo.muzykant.controllers.RestMappings;

@Configuration
public class AppConfig implements WebMvcConfigurer {
  private @Value("${app.storage.cloudinaryurl}") String cloudinaryUrl;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new SpecificationArgumentResolver());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Profile("prod")
  public Cloudinary cloudinary() {
    return new Cloudinary(cloudinaryUrl);
  }

  @Bean("imageDownloadUri")
  @Profile("dev")
  public String devImageDownloadUri() {
    return "http://localhost:8080" + RestMappings.USER_IMAGE + RestMappings.IMAGE_UPLOADS + "/";
  }

  @Bean("imageDownloadUri")
  @Profile("prod")
  public String prodImageDownloadUri() {
    return cloudinary().url().generate("/image-uploads");
  }
}
