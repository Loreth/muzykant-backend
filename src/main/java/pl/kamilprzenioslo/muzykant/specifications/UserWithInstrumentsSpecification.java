package pl.kamilprzenioslo.muzykant.specifications;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.InstrumentEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.InstrumentEntity_;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity_;

@Data
public class UserWithInstrumentsSpecification<T extends UserEntity> implements Specification<T> {

  private final List<Integer> instrumentIds;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    if (instrumentIds != null) {
      SetJoin<T, InstrumentEntity> genreJoin = root.join(UserEntity_.instruments);
      cq.distinct(true).where(genreJoin.get(InstrumentEntity_.id).in(instrumentIds));
    }

    return cq.getRestriction();
  }
}
