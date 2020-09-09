package pl.kamilprzenioslo.muzykant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import pl.kamilprzenioslo.muzykant.security.UserAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority extends BaseDto<Integer> implements GrantedAuthority {

  private UserAuthority userAuthority;

  @Override
  public String getAuthority() {
    return userAuthority.name();
  }
}
