package pl.kamilprzenioslo.muzykant.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import pl.kamilprzenioslo.muzykant.security.JwtUtils;
import pl.kamilprzenioslo.muzykant.service.CredentialsService;

@RequiredArgsConstructor
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketAuthenticationConfig implements WebSocketMessageBrokerConfigurer {

  private final JwtUtils jwtUtils;
  private final CredentialsService credentialsService;

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(
        new ChannelInterceptor() {
          @Override
          public Message<?> preSend(Message<?> message, MessageChannel channel) {
            StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
              String authorizationHeader =
                  accessor.getNativeHeader(HttpHeaders.AUTHORIZATION).get(0);
              log.debug(
                  "Authenticating CONNECT web socket message with header: {}", authorizationHeader);

              UsernamePasswordAuthenticationToken authentication =
                  getAuthentication(authorizationHeader);
              accessor.setUser(authentication);
            }
            return message;
          }
        });
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String authorizationHeader) {
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
