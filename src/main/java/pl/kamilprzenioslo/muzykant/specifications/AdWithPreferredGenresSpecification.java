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
import pl.kamilprzenioslo.muzykant.persistance.entities.AdPreferredGenreEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.AdPreferredGenreEntity_;

@Data
public class AdWithPreferredGenresSpecification<T extends AdEntity> implements Specification<T> {

  private final List<Integer> preferredGenreIds;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    if (preferredGenreIds != null) {
      Subquery<AdPreferredGenreEntity> subquery = cq.subquery(AdPreferredGenreEntity.class);
      Root<AdPreferredGenreEntity> subqueryRoot = subquery.from(AdPreferredGenreEntity.class);

      cq.distinct(true)
          .where(
              root.get(AdEntity_.id)
                  .in(
                      subquery
                          .select(subqueryRoot.get(AdPreferredGenreEntity_.AD_ID))
                          .where(
                              subqueryRoot
                                  .get(AdPreferredGenreEntity_.GENRE_ID)
                                  .in(preferredGenreIds))
                          .groupBy(subqueryRoot.get(AdPreferredGenreEntity_.AD_ID))
                          .having(
                              cb.greaterThanOrEqualTo(
                                  cb.count(subqueryRoot.get(AdPreferredGenreEntity_.GENRE_ID)),
                                  (long) preferredGenreIds.size()))));
    }

    return cq.getRestriction();
  }
}
