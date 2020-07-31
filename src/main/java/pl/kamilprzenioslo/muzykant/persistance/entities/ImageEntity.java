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
@Table(name = "image")
public class ImageEntity extends AbstractPersistable<Integer> {

  private String link;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

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
    ImageEntity that = (ImageEntity) o;
    return link.equals(that.link);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), link);
  }
}
