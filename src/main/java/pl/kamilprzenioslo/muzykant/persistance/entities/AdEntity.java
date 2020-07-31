package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Ad")
public class AdEntity extends AbstractPersistable<Integer> {

  @Column(name = "published_date")
  private LocalDate publishedDate;

  private String location;
  private String description;
  private boolean commercial;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "Ad_preferred_genre",
      joinColumns = @JoinColumn(name = "ad_id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<GenreEntity> preferredGenres;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "Ad_preferred_instrument",
      joinColumns = @JoinColumn(name = "ad_id"),
      inverseJoinColumns = @JoinColumn(name = "instrument_id"))
  private Set<InstrumentEntity> preferredInstruments;

  @Override
  public int hashCode() {
    // necessary due to lack of a natural key - can't use only ID in hashCode, as object can't
    // change it's hashcode after being added to Set
    return 1;
  }
}
