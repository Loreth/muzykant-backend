package pl.kamilprzenioslo.muzykant.dtos.security;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordChangeRequest {

  @NotNull private String currentPassword;
  @NotNull private String newPassword;
}
