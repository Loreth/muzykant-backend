package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.kamilprzenioslo.muzykant.persistance.enums.AdType.Values;

@Getter
@Setter
@ToString
@Entity
@Table(name = "musician_wanted_ad")
@DiscriminatorValue(Values.MUSICIAN_WANTED)
@PrimaryKeyJoinColumn(name = "ad_id")
public class MusicianWantedAdEntity extends AdEntity {

  @Column(name = "preferred_gender")
  private String preferredGender;

  @Column(name = "min_age")
  private Byte minAge;

  @Column(name = "max_age")
  private Byte maxAge;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "vocal_range_id")
  private VocalRangeEntity vocalRange;
}
