package pl.kamilprzenioslo.muzykant.dtos;

import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User extends BaseDto<Integer> {

  private String linkName;
  private String description;
  private String phone;
  private String city;
  private Voivodeship voivodeship;
  private Credentials credentials;
  private Set<Ad> ads;
  private Set<Image> images;
  private Set<Genre> genres;
  private Set<Instrument> instruments;
  private Set<VocalTechnique> vocalTechniques;
}
