package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
@Table(name = "band")
@DiscriminatorValue(Values.BAND)
@PrimaryKeyJoinColumn(name = "user_id")
public class BandEntity extends UserEntity {

  private String name;

  @Column(name = "formation_year")
  private Short formationYear;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    BandEntity that = (BandEntity) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }

  @Override
  public String getDisplayName() {
    return name;
  }
}
