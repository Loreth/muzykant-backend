package pl.kamilprzenioslo.muzykant.dtos.security;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

  @NotNull @Email private String email;

  @NotNull
  @Size(min = 8)
  private String password;
}
