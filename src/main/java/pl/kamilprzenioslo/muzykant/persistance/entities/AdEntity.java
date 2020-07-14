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
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Ad")
public class AdEntity extends AbstractPersistable<Integer> {

  @Column(name = "published_date")
  private LocalDate publishedDate;

  private String description;

  @Column(name = "preferred_gender")
  private Character preferredGender;

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

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "Ad_preferred_vocal_technique",
      joinColumns = @JoinColumn(name = "ad_id"),
      inverseJoinColumns = @JoinColumn(name = "vocal_technique_id"))
  private Set<VocalTechniqueEntity> preferredVocalTechniques;
}
