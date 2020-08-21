package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import pl.kamilprzenioslo.muzykant.persistance.AdType;

@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ad_type")
@Table(name = "Ad")
public abstract class AdEntity extends AbstractPersistable<Integer> {

  @Enumerated(EnumType.STRING)
  @Column(name = "ad_type", nullable = false, insertable = false, updatable = false)
  private AdType adType;

  @Column(name = "published_date")
  private LocalDate publishedDate;

  private String location;
  private String description;
  private boolean commercial;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "ad_voivodeship",
      joinColumns = @JoinColumn(name = "ad_id"),
      inverseJoinColumns = @JoinColumn(name = "voivodeship_id"))
  private Set<VoivodeshipEntity> voivodeships;

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
}
