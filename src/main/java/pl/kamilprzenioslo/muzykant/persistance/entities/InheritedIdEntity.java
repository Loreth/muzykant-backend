package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import lombok.Data;
import org.springframework.data.domain.Persistable;

@MappedSuperclass
@Data
public abstract class InheritedIdEntity<ID extends Serializable> implements Persistable<ID> {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(unique = true, nullable = false)
  protected ID id;

  // enables JPA to recognize that entity is new despite having non-null id
  @Version private Integer version;

  @Transient
  @Override
  public boolean isNew() {
    return version == null;
  }
}
