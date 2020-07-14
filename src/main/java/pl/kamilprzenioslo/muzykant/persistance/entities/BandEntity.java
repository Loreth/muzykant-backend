package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "band")
@PrimaryKeyJoinColumn(name = "user_id")
public class BandEntity extends UserEntity {

  private String name;

  @Column(name = "formation_year")
  private Short formationYear;
}
