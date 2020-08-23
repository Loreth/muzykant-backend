package pl.kamilprzenioslo.muzykant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority extends BaseDto<Integer> {

  private UserAuthority userAuthority;
}
