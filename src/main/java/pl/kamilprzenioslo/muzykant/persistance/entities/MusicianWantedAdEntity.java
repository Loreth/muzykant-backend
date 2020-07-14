package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "musician_wanted_ad")
@PrimaryKeyJoinColumn(name = "ad_id")
public class MusicianWantedAdEntity extends AdEntity {

  @Column(name = "min_age")
  private Byte minAge;

  @Column(name = "max_age")
  private Byte maxAge;

  @ManyToOne
  @JoinColumn(name = "vocal_range_id")
  private VocalRangeEntity vocalRange;
}
