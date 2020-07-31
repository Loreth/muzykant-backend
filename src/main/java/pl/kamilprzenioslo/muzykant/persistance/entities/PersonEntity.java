package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "person")
public class PersonEntity extends AbstractPersistable<Integer> {

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  private String pseudo;
  private Character gender;
  private LocalDate birthdate;
}
