package pl.kamilprzenioslo.muzykant.security;

import io.jsonwebtoken.JwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final AuthenticationTokenProvider authenticationTokenProvider;
  private final HandlerExceptionResolver handlerExceptionResolver;

  public JwtAuthorizationFilter(
      AuthenticationManager authManager,
      AuthenticationTokenProvider authenticationTokenProvider,
      HandlerExceptionResolver handlerExceptionResolver) {
    super(authManager);
    this.authenticationTokenProvider = authenticationTokenProvider;
    this.handlerExceptionResolver = handlerExceptionResolver;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    try {
      UsernamePasswordAuthenticationToken authentication =
          authenticationTokenProvider.getAuthentication(authorizationHeader);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      chain.doFilter(request, response);
    } catch (UsernameNotFoundException | JwtException ex) {
      handlerExceptionResolver.resolveException(request, response, null, ex);
    }
  }
}
