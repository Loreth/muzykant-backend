package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "predefined_vocal_range")
@PrimaryKeyJoinColumn(name = "vocal_range_id")
public class PredefinedVocalRangeEntity extends VocalRangeEntity {

  private String name;
}
