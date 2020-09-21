package pl.kamilprzenioslo.muzykant.specifications;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity_;
import pl.kamilprzenioslo.muzykant.persistance.entities.VoivodeshipEntity_;

@Data
public class UserWithVoivodeshipsSpecification<T extends UserEntity> implements Specification<T> {

  private final List<Integer> voivodeshipIds;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    if (voivodeshipIds != null) {
      //      SetJoin<T, GenreEntity> genreJoin = root.join(UserEntity_.genres);
      cq.where(root.get(UserEntity_.voivodeship).get(VoivodeshipEntity_.id).in(voivodeshipIds));
    }

    return cq.getRestriction();
  }
}
