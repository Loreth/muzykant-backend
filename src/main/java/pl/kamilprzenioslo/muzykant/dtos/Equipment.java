package pl.kamilprzenioslo.muzykant.dtos;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment extends BaseDto<Integer> {
  @NotNull private String name;
  @NotNull
  private Integer musicianId;
}
