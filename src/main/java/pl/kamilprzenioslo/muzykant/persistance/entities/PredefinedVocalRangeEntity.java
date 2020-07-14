package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "predefined_vocal_range")
public class PredefinedVocalRangeEntity extends VocalRangeEntity {

  private String name;
}
