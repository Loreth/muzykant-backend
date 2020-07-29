package pl.kamilprzenioslo.muzykant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamilprzenioslo.muzykant.persistance.UserType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials extends BaseDto<Integer> {

  private String email;
  private String password;
  private Integer userId;
  private UserType userType;
}
