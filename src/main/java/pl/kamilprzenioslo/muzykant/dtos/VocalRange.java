package pl.kamilprzenioslo.muzykant.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VocalRange extends BaseDto<Integer> {

  private String lowestNote;
  private String highestNote;
}
