package pl.kamilprzenioslo.muzykant.dtos;

import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ad extends BaseDto<Integer> {

  private LocalDate publishedDate;
  private String location;
  private String description;
  private boolean commercial;
  private Set<Genre> preferredGenres;
  private Set<Instrument> preferredInstruments;
  @NotNull private Integer userId;
}
