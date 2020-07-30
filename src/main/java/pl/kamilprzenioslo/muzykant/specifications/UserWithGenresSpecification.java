package pl.kamilprzenioslo.muzykant.specifications;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.GenreEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.GenreEntity_;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity_;

@Data
public class UserWithGenresSpecification<T extends UserEntity> implements Specification<T> {

  private final List<Integer> genreIds;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    if (genreIds != null) {
      SetJoin<T, GenreEntity> genreJoin = root.join(UserEntity_.genres);
      cq.distinct(true).where(genreJoin.get(GenreEntity_.id).in(genreIds));
    }

    return cq.getRestriction();
  }
}
