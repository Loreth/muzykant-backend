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
import pl.kamilprzenioslo.muzykant.persistance.entities.GenreEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.GenreEntity_;

@Data
public class AdWithPreferredGenresSpecification<T extends AdEntity> implements Specification<T> {

  private final List<Integer> preferredGenreIds;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    if (preferredGenreIds != null) {
      SetJoin<T, GenreEntity> genreJoin = root.join(AdEntity_.preferredGenres);
      cq.distinct(true).where(genreJoin.get(GenreEntity_.id).in(preferredGenreIds));
    }

    return cq.getRestriction();
  }
}
