package pl.kamilprzenioslo.muzykant.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Instrument extends BaseDto<Integer> {

  private String name;
  private String playerName;
}
