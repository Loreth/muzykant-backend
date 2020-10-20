package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "musician")
@DiscriminatorValue(Values.MUSICIAN)
@PrimaryKeyJoinColumn(name = "user_profile_id")
public class MusicianEntity extends UserEntity {

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id", nullable = false)
  private PersonEntity person;

  @OneToMany(mappedBy = "musician", fetch = FetchType.LAZY)
  @Column(insertable = false, updatable = false)
  private Set<EquipmentEntity> equipment;

  @Override
  public String getDisplayName() {
    return person.getDisplayName();
  }
}
