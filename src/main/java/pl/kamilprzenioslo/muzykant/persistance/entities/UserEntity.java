package pl.kamilprzenioslo.muzykant.persistance.entities;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
public class UserEntity extends AbstractPersistable<Integer> {

  @Column(name = "link_name")
  private String linkName;

  private String description;
  private String phone;
  private String city;

  @ManyToOne
  @JoinColumn(name = "voivodeship_id", nullable = false)
  private VoivodeshipEntity voivodeship;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<AdEntity> ads;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<ImageEntity> images;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_genre",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<GenreEntity> genres;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_instrument",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "instrument_id"))
  private Set<InstrumentEntity> instruments;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "user_vocal_technique",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "vocal_technique_id"))
  private Set<VocalTechniqueEntity> vocalTechniques;
}
