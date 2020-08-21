package pl.kamilprzenioslo.muzykant.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamilprzenioslo.muzykant.persistance.AdType;
import pl.kamilprzenioslo.muzykant.persistance.UserType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ad extends BaseDto<Integer> {

  private AdType adType;
  private LocalDate publishedDate;
  private Set<Voivodeship> voivodeships;
  private String location;
  private String description;
  private boolean commercial;
  private Set<Genre> preferredGenres;
  private Set<Instrument> preferredInstruments;
  @NotNull private Integer userId;
  private UserType userType;
  private String userDisplayName;
  private List<Genre> userGenres;
  private String userProfileImageLink;
}
