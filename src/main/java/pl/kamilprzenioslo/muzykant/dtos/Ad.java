package pl.kamilprzenioslo.muzykant.dtos;

import java.time.LocalDate;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ad extends BaseDto<Integer> {

  private LocalDate publishedDate;
  private String description;
  private Character preferredGender;
  private boolean commercial;
  private Set<Genre> preferredGenres;
  private Set<Instrument> preferredInstruments;
  private Set<VocalTechnique> preferredVocalTechniques;
}
