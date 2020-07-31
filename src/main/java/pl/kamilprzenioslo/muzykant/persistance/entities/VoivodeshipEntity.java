package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "voivodeship")
public class VoivodeshipEntity extends AbstractPersistable<Integer> {

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
    VoivodeshipEntity that = (VoivodeshipEntity) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }
}
