package pl.kamilprzenioslo.muzykant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre extends BaseDto<Integer> {

  private String name;
}
