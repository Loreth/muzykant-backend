package pl.kamilprzenioslo.muzykant.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.kamilprzenioslo.muzykant.security.JwtAuthenticationFilter;
import pl.kamilprzenioslo.muzykant.security.JwtAuthorizationFilter;
import pl.kamilprzenioslo.muzykant.security.JwtUtils;
import pl.kamilprzenioslo.muzykant.security.UnauthorizedAuthenticationEntryPoint;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final CredentialsService credentialsService;
  private final PasswordEncoder passwordEncoder;
  private final ObjectMapper objectMapper;
  private final JwtUtils jwtUtils;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilter(
        new JwtAuthenticationFilter(
            jwtUtils, objectMapper, credentialsService, authenticationManager()))
        .addFilter(
            new JwtAuthorizationFilter(authenticationManager(), jwtUtils, credentialsService))
        .cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(new UnauthorizedAuthenticationEntryPoint())
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .authorizeRequests()
        .anyRequest()
        .permitAll();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(credentialsService).passwordEncoder(passwordEncoder);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    var corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
    //    corsConfiguration.setExposedHeaders(
    //        Arrays.asList(
    //            HEADER_STRING_AUTH,
    //            HEADER_STRING_ID,
    //            HEADER_STRING_REFRESH,
    //            HEADER_STRING_TYPE,
    //            HEADER_STRING_PERSON));
    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }
}
