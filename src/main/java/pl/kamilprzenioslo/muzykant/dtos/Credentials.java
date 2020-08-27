package pl.kamilprzenioslo.muzykant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials extends BaseDto<Integer> {

  private String email;
  private String password;
  private Authority authority;
  private Integer userId;
  private String linkName;
}
