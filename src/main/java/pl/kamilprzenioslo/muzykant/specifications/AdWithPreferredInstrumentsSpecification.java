package pl.kamilprzenioslo.muzykant.specifications;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.AdEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.AdEntity_;
import pl.kamilprzenioslo.muzykant.persistance.entities.InstrumentEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.InstrumentEntity_;

@Data
public class AdWithPreferredInstrumentsSpecification<T extends AdEntity>
    implements Specification<T> {

  private final List<Integer> preferredInstrumentIds;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    if (preferredInstrumentIds != null) {
      SetJoin<T, InstrumentEntity> join = root.join(AdEntity_.preferredInstruments);
      cq.distinct(true).where(join.get(InstrumentEntity_.id).in(preferredInstrumentIds));
    }

    return cq.getRestriction();
  }
}
