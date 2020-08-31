package pl.kamilprzenioslo.muzykant.dtos;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials extends BaseDto<Integer> implements UserDetails {

  private String email;
  private String password;
  private Authority authority;
  private Integer userId;
  private String linkName;
  private Integer emailConfirmationId;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (authority == null) {
      return Collections.emptyList();
    }
    return List.of(authority);
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return emailConfirmationId == null;
  }
}
