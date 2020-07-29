package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vocal_technique")
public class VocalTechniqueEntity extends AbstractPersistable<Integer> {

  private String name;
}
