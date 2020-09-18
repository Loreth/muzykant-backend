package pl.kamilprzenioslo.muzykant.dtos.security;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamilprzenioslo.muzykant.dtos.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifiedEmailSignUpRequest<T extends User> {

  @NotNull private String email;
  @NotNull private T user;
}
