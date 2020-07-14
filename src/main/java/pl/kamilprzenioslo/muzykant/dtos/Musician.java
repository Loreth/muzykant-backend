package pl.kamilprzenioslo.muzykant.dtos;

import java.util.Set;
import lombok.Data;

@Data
public class Musician extends User {

  private Person person;
  private VocalRange vocalRange;
  private Set<Equipment> equipment;
}
