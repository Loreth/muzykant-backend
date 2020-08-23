package pl.kamilprzenioslo.muzykant.dtos;

import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseDto<Integer> {

  private UserType userType;
  @NotNull private String linkName;
  private String description;
  private String phone;
  private String city;
  private Voivodeship voivodeship;
  private String profileImageLink;
  private Set<UserImage> userImages;
  private Set<Genre> genres;
  private Set<Instrument> instruments;
  private Set<VocalTechnique> vocalTechniques;
}
