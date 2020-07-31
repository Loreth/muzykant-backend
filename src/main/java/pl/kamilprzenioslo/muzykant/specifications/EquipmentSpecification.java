package pl.kamilprzenioslo.muzykant.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.EquipmentEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.EquipmentEntity_;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianEntity_;

@Data
public class EquipmentSpecification implements Specification<EquipmentEntity> {
  private final Integer musicianId;

  @Override
  public Predicate toPredicate(
      Root<EquipmentEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    if (musicianId != null) {
      Join<EquipmentEntity, MusicianEntity> musicianJoin = root.join(EquipmentEntity_.musician);
      cq.where(cb.equal(musicianJoin.get(MusicianEntity_.id), musicianId));
    }

    return cq.getRestriction();
  }
}
