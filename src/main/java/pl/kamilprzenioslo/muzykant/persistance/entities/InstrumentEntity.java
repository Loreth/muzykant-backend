package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "instrument")
public class InstrumentEntity extends AbstractPersistable<Integer> {

  private String name;

  @Column(name = "player_name")
  private String playerName;
}
