package pl.kamilprzenioslo.muzykant.dtos;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialMediaLinks implements IdentifiableDto<Integer> {

  private String youtube;
  private String soundcloud;
  private String webpage;
  @NotNull private Integer userId;

  @Override
  public Integer getId() {
    return userId;
  }

  @Override
  public void setId(Integer id) {
    this.userId = id;
  }
}
