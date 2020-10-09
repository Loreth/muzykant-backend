package pl.kamilprzenioslo.muzykant.dtos;

import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseDto<Integer> {

  private UserType userType;

  @NotNull
  @Size(max = 30)
  private String linkName;

  private String displayName;

  @Size(max = 1000)
  private String description;

  @Size(max = 60)
  private String phone;

  @Size(max = 60)
  private String city;

  @NotNull private Voivodeship voivodeship;
  private String profileImageLink;
  private Set<UserImage> userImages;
  private Set<Genre> genres;
  private Set<Instrument> instruments;
  private SocialMediaLinks socialMediaLinks;
}
