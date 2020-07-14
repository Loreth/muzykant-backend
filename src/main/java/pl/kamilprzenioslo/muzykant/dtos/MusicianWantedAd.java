package pl.kamilprzenioslo.muzykant.dtos;

import lombok.Data;

@Data
public class MusicianWantedAd extends Ad {

  private Byte minAge;
  private Byte maxAge;
  private VocalRange vocalRange;
}
