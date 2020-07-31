package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "predefined_vocal_range")
@PrimaryKeyJoinColumn(name = "vocal_range_id")
public class PredefinedVocalRangeEntity extends VocalRangeEntity {

  private String name;

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
    PredefinedVocalRangeEntity that = (PredefinedVocalRangeEntity) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }
}
