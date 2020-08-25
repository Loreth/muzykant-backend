package pl.kamilprzenioslo.muzykant.security;

import static pl.kamilprzenioslo.muzykant.security.JwtConstants.JWT_PREFIX;

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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final JwtUtils jwtUtils;
  private final UserDetailsService userDetailsService;

  public JwtAuthorizationFilter(
      AuthenticationManager authManager, JwtUtils jwtUtils, UserDetailsService userDetailsService) {
    super(authManager);
    this.jwtUtils = jwtUtils;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header != null && header.startsWith(JWT_PREFIX)) {
      UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = jwtUtils.parseJwtHeader(request.getHeader(HttpHeaders.AUTHORIZATION));

    if (token != null) {
      jwtUtils.validateToken(token);
      String username = jwtUtils.getUsernameFromToken(token);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      return new UsernamePasswordAuthenticationToken(
          username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    return null;
  }
}
