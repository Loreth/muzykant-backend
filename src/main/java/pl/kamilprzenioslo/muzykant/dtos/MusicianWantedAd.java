package pl.kamilprzenioslo.muzykant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicianWantedAd extends Ad {

  private String preferredGender;
  private Short minAge;
  private Short maxAge;
}
