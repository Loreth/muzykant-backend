package pl.kamilprzenioslo.muzykant.config;

import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.BAND;
import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.BAND_WANTED_AD;
import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.CONFIRM_EMAIL;
import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.EQUIPMENT;
import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.ID;
import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.IMAGE_UPLOAD;
import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.JAM_SESSION_AD;
import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.MUSICIAN;
import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.MUSICIAN_WANTED_AD;
import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.REGULAR_USER;
import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.RESEND_MAIL;
import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.SIGN_UP;
import static pl.kamilprzenioslo.muzykant.controllers.RestMappings.USER_IMAGE;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.kamilprzenioslo.muzykant.security.CustomAuthenticationFailureHandler;
import pl.kamilprzenioslo.muzykant.security.JwtAuthenticationFilter;
import pl.kamilprzenioslo.muzykant.security.JwtAuthorizationFilter;
import pl.kamilprzenioslo.muzykant.security.JwtUtils;
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
            jwtUtils,
            objectMapper,
            authenticationManager(),
            credentialsService,
            authenticationFailureHandler()))
        .addFilter(
            new JwtAuthorizationFilter(authenticationManager(), jwtUtils, credentialsService))
        .exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        .and()
        .cors()
        .and()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .httpBasic()
        .disable()
        .formLogin()
        .loginProcessingUrl("/login")
        .loginPage("/login")
        .permitAll()
        .and()
        .authorizeRequests()
        .antMatchers(SIGN_UP, CONFIRM_EMAIL, RESEND_MAIL)
        .permitAll()
        .antMatchers(HttpMethod.GET, "/**")
        .permitAll()
        .antMatchers(
            HttpMethod.POST,
            MUSICIAN,
            BAND,
            REGULAR_USER,
            BAND_WANTED_AD,
            MUSICIAN_WANTED_AD,
            JAM_SESSION_AD,
            RESEND_MAIL,
            IMAGE_UPLOAD,
            EQUIPMENT,
            USER_IMAGE + "/**")
        .authenticated()
        .antMatchers(MUSICIAN + ID)
        .access(
            "hasRole('MUSICIAN') and @accessSecurity.hasFullAccessToUserResource(authentication,#id)")
        .antMatchers(BAND + ID)
        .access(
            "hasRole('BAND') and @accessSecurity.hasFullAccessToUserResource(authentication,#id)")
        .antMatchers(REGULAR_USER + ID)
        .access(
            "hasRole('REGULAR_USER') and @accessSecurity.hasFullAccessToUserResource(authentication,#id)")
        .antMatchers(USER_IMAGE + "/{id:\\d+}")
        .access(
            "isAuthenticated() and @accessSecurity.hasFullAccessToUserImage(authentication,#id)")
        .antMatchers(EQUIPMENT + ID)
        .access(
            "hasRole('MUSICIAN') and @accessSecurity.hasFullAccessToEquipment(authentication,#id)")
        .antMatchers(MUSICIAN_WANTED_AD + ID, BAND_WANTED_AD + ID, JAM_SESSION_AD + ID)
        .access("isAuthenticated() and @accessSecurity.hasFullAccessToAd(authentication,#id)")
        .anyRequest()
        .denyAll();
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

    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

  @Bean
  AuthenticationFailureHandler authenticationFailureHandler() {
    return new CustomAuthenticationFailureHandler(objectMapper);
  }
}
