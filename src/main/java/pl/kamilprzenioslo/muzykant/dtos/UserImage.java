package pl.kamilprzenioslo.muzykant.dtos;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserImage extends BaseDto<Integer> {
  private String filename;
  @NotNull private String link;
  @NotNull private Integer userId;
  @NotNull private int orderIndex;
}
