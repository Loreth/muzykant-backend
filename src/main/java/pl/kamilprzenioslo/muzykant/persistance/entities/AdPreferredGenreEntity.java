package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ad_preferred_genre")
public class AdPreferredGenreEntity implements Serializable {
  @Id
  @Column(name = "ad_id")
  private Integer adId;

  @Id
  @Column(name = "genre_id")
  private Integer genreId;
}
