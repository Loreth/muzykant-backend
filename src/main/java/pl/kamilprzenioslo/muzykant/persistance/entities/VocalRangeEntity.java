package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "vocal_range")
public class VocalRangeEntity extends AbstractPersistable<Integer> {

  @Column(name = "lowest_note")
  private String lowestNote;

  @Column(name = "highest_note")
  private String highestNote;
}
