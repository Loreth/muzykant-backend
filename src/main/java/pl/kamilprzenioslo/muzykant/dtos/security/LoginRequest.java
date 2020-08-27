package pl.kamilprzenioslo.muzykant.dtos.security;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

  @NotNull
  private String username;
  @NotNull
  private String password;
}
