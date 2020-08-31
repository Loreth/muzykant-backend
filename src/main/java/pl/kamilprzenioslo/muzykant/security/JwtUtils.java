package pl.kamilprzenioslo.muzykant.security;

import static pl.kamilprzenioslo.muzykant.security.JwtConstants.JWT_PREFIX;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.kamilprzenioslo.muzykant.dtos.Credentials;

@Slf4j
@Component
public class JwtUtils {

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Value("${app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateToken(Credentials principal) {
    Claims claims = Jwts.claims();
    if (principal.getAuthority() != null) {
      claims.put("authority", principal.getAuthority().getAuthority());
    }
    claims.put("userId", principal.getUserId());
    claims.put("linkName", principal.getLinkName());

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(principal.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
        .compact();
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public void validateToken(String token) {
    Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
        .build()
        .parseClaimsJws(token);
  }

  public String parseJwtHeader(String header) {
    if (header != null && header.startsWith(JWT_PREFIX)) {
      return header.substring(7);
    }

    return null;
  }
}
