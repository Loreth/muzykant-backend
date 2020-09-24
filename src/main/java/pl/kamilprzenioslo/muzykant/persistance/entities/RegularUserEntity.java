package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.kamilprzenioslo.muzykant.persistance.enums.UserType.Values;

@Getter
@Setter
@ToString
@Entity
@Table(name = "regular_user")
@DiscriminatorValue(Values.REGULAR)
@PrimaryKeyJoinColumn(name = "user_profile_id")
public class RegularUserEntity extends UserEntity {

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id")
  private PersonEntity person;

  @Override
  public String getDisplayName() {
    return person.getDisplayName();
  }
}
