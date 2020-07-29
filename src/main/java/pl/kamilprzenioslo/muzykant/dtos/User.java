package pl.kamilprzenioslo.muzykant.dtos;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseDto<Integer> {

  private String linkName;
  private String description;
  private String phone;
  private String city;
  private Voivodeship voivodeship;
  private Set<Genre> genres;
  private Set<Instrument> instruments;
  private Set<VocalTechnique> vocalTechniques;
}
