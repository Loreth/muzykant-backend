package pl.kamilprzenioslo.muzykant.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Equipment extends BaseDto<Integer> {

  private String name;
  private Musician musician;
}
