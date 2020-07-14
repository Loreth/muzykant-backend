package pl.kamilprzenioslo.muzykant.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Image extends BaseDto<Integer> {

  private String link;
  private User user;
}
