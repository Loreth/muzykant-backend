package pl.kamilprzenioslo.muzykant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicianWantedAd extends Ad {

  private Character preferredGender;
  private Byte minAge;
  private Byte maxAge;
  private VocalRange vocalRange;
}
