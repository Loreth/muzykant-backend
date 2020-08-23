package pl.kamilprzenioslo.muzykant.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserEntity_;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserImageEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.UserImageEntity_;

@Data
public class UserImageSpecification implements Specification<UserImageEntity> {
  private final Integer userId;

  @Override
  public Predicate toPredicate(
      Root<UserImageEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    if (userId != null) {
      Join<UserImageEntity, UserEntity> userJoin = root.join(UserImageEntity_.user);
      cq.where(cb.equal(userJoin.get(UserEntity_.id), userId));
    }

    return cq.getRestriction();
  }
}
