package pl.kamilprzenioslo.muzykant.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final JwtUtils jwtUtils;
  private final UserDetailsService userDetailsService;
  private final HandlerExceptionResolver handlerExceptionResolver;

  public JwtAuthorizationFilter(
      AuthenticationManager authManager,
      JwtUtils jwtUtils,
      UserDetailsService userDetailsService,
      HandlerExceptionResolver handlerExceptionResolver) {
    super(authManager);
    this.jwtUtils = jwtUtils;
    this.userDetailsService = userDetailsService;
    this.handlerExceptionResolver = handlerExceptionResolver;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    try {
      UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationHeader);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      chain.doFilter(request, response);
    } catch (UsernameNotFoundException ex) {
      handlerExceptionResolver.resolveException(request, response, null, ex);
    }
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String authorizationHeader) {
    String token = jwtUtils.parseJwtHeader(authorizationHeader);

    if (token != null) {
      jwtUtils.validateToken(token);
      String username = jwtUtils.getUsernameFromToken(token);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      return new UsernamePasswordAuthenticationToken(
          userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    return null;
  }
}
