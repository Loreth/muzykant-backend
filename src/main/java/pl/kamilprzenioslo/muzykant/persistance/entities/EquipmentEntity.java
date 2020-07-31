package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "equipment")
public class EquipmentEntity extends AbstractPersistable<Integer> {

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "musician_user_id", nullable = false)
  private MusicianEntity musician;

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
    EquipmentEntity that = (EquipmentEntity) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }
}
