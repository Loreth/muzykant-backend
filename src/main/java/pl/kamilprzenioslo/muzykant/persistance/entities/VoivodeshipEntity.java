package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "voivodeship")
public class VoivodeshipEntity extends AbstractPersistable<Integer> {

  private String name;
}
