package pl.kamilprzenioslo.muzykant.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;

@Component
@RequiredArgsConstructor
public class AuthenticationTokenProvider {
  private final JwtUtils jwtUtils;
  private final CredentialsService credentialsService;

  public UsernamePasswordAuthenticationToken getAuthentication(String authorizationHeader) {
    String token = jwtUtils.parseJwtHeader(authorizationHeader);

    if (token != null) {
      jwtUtils.validateToken(token);
      String username = jwtUtils.getUsernameFromToken(token);
      UserDetails userDetails = credentialsService.loadUserByUsername(username);
      return new UsernamePasswordAuthenticationToken(
          userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    return null;
  }
}
