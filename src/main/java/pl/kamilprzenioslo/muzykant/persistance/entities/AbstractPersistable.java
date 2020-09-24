package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

/**
 * Based on Spring's AbstractPersistable, this implementation differs mainly in setId() being public
 * instead of protected (which allows for DTO to Entity mapping)
 */
@MappedSuperclass
public abstract class AbstractPersistable<ID extends Serializable> implements Persistable<ID> {

  @Id @GeneratedValue @Nullable private ID id;

  @Nullable
  @Override
  public ID getId() {
    return id;
  }

  public void setId(@Nullable ID id) {
    this.id = id;
  }

  @Transient // DATAJPA-622
  @Override
  public boolean isNew() {
    return null == getId();
  }

  @Override
  public String toString() {
    return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractPersistable<?> that = (AbstractPersistable<?>) o;
    return Objects.equals(id, that.id);
  }

  /** Override only if entity contains a natural key - do not use any other field */
  @Override
  public int hashCode() {
    // necessary, because we can't simply use ID in hashCode, as object can't change its hashcode
    // after being added to a Set
    return 1;
  }
}
