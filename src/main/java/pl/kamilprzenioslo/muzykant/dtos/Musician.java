package pl.kamilprzenioslo.muzykant.dtos;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Musician extends User {

  private Person person;
  private VocalRange vocalRange;
  private Set<Equipment> equipment;
}
