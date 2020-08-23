package pl.kamilprzenioslo.muzykant.service.mapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.TargetType;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EntityReferenceFactory {

  @PersistenceContext private EntityManager em;

  public <T extends Persistable<?>> T resolveForeignKeyEntityReference(
      Integer id, @TargetType Class<T> entityClass) {
    T entity = null;

    if (id != null) {
      entity = em.getReference(entityClass, id);
    }

    return entity;
  }
}
