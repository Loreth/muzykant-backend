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
import org.springframework.data.jpa.domain.AbstractPersistable;

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "credentials_id", nullable = false)
  private CredentialsEntity credentials;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<AdEntity> ads;

  @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
  private Set<ImageEntity> images;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "ad_preferred_genre")
  private Set<GenreEntity> genres;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "ad_preferred_instrument")
  private Set<InstrumentEntity> instruments;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "ad_preferred_vocal_technique")
  private Set<VocalTechniqueEntity> vocalTechniques;
}
