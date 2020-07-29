package pl.kamilprzenioslo.muzykant.specifications;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.AdEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.AdEntity_;
import pl.kamilprzenioslo.muzykant.persistance.entities.AdPreferredInstrumentEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.AdPreferredInstrumentEntity_;

@Data
public class AdWithPreferredInstrumentsSpecification<T extends AdEntity>
    implements Specification<T> {

  private final List<Integer> preferredInstrumentIds;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    if (preferredInstrumentIds != null) {
      Subquery<AdPreferredInstrumentEntity> subquery =
          cq.subquery(AdPreferredInstrumentEntity.class);
      Root<AdPreferredInstrumentEntity> subqueryRoot =
          subquery.from(AdPreferredInstrumentEntity.class);

      cq.distinct(true)
          .where(
              root.get(AdEntity_.id)
                  .in(
                      subquery
                          .select(subqueryRoot.get(AdPreferredInstrumentEntity_.AD_ID))
                          .where(
                              subqueryRoot
                                  .get(AdPreferredInstrumentEntity_.INSTRUMENT_ID)
                                  .in(preferredInstrumentIds))
                          .groupBy(subqueryRoot.get(AdPreferredInstrumentEntity_.AD_ID))
                          .having(
                              cb.greaterThanOrEqualTo(
                                  cb.count(
                                      subqueryRoot.get(AdPreferredInstrumentEntity_.INSTRUMENT_ID)),
                                  (long) preferredInstrumentIds.size()))));
    }

    return cq.getRestriction();
  }
}
