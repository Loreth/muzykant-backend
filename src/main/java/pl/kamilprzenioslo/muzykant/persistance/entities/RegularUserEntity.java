package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "regular_user")
@PrimaryKeyJoinColumn(name = "user_id")
public class RegularUserEntity extends UserEntity {

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id")
  private PersonEntity person;
}
