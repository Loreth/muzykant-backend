package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

/**
 * Based on Spring's AbstractPersistable, this implementation differs in setId() being public
 * instead of protected (which allows for DTO to Entity mapping)
 */
@MappedSuperclass
@EqualsAndHashCode
public abstract class AbstractPersistable<ID extends Serializable> implements Persistable<ID> {

  @Id @GeneratedValue private @Nullable ID id;

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
}
