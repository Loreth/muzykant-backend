package pl.kamilprzenioslo.muzykant.dtos;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Musician extends User {

  @NotNull private Person person;
}
