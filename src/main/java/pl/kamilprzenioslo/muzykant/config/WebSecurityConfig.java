package pl.kamilprzenioslo.muzykant.config;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.kamilprzenioslo.muzykant.security.JwtAuthenticationEntryPoint;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthenticationEntryPoint unauthorizedHandler;
  //  private final JwtAuthenticationFilter authenticationTokenFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/")
        .permitAll()
        .anyRequest()
        .permitAll();

    //    http.addFilterBefore(authenticationTokenFilter,
    // UsernamePasswordAuthenticationFilter.class);
  }

  //  @Override
  //  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws
  // Exception {
  //
  // authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  //  }

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

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
