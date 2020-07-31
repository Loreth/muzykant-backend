package pl.kamilprzenioslo.muzykant.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.ImageEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.ImageEntity_;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity_;

@Data
public class ImageSpecification implements Specification<ImageEntity> {
  private final Integer userId;

  @Override
  public Predicate toPredicate(Root<ImageEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    if (userId != null) {
      Join<ImageEntity, UserEntity> userJoin = root.join(ImageEntity_.user);
      cq.where(cb.equal(userJoin.get(UserEntity_.id), userId));
    }

    return cq.getRestriction();
  }
}
