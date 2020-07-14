package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "regular_user")
public class RegularUserEntity extends UserEntity {

  @OneToOne
  @JoinColumn(name = "person_id")
  private PersonEntity person;
}
