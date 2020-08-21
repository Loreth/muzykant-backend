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
import pl.kamilprzenioslo.muzykant.persistance.entities.VoivodeshipEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.VoivodeshipEntity_;

@Data
public class AdWithVoivodeshipsSpecification<T extends AdEntity> implements Specification<T> {

  private final List<Integer> voivodeshipIds;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    if (voivodeshipIds != null) {
      SetJoin<T, VoivodeshipEntity> genreJoin = root.join(AdEntity_.voivodeships);
      cq.distinct(true).where(genreJoin.get(VoivodeshipEntity_.id).in(voivodeshipIds));
    }

    return cq.getRestriction();
  }
}
