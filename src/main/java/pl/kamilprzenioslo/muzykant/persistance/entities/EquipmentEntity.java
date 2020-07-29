package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "equipment")
public class EquipmentEntity extends AbstractPersistable<Integer> {

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "musician_user_id", nullable = false)
  private MusicianEntity musician;
}
