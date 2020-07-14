package pl.kamilprzenioslo.muzykant.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Credentials extends BaseDto<Integer> {

  private String email;
  private String username;
  private String password;
}
