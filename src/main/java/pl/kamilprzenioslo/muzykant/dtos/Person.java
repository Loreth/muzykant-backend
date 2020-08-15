package pl.kamilprzenioslo.muzykant.dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseDto<Integer> {

  private String firstName;
  private String lastName;
  private String pseudo;
  private String gender;
  private LocalDate birthdate;
}
