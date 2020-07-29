package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ad_preferred_instrument")
public class AdPreferredInstrumentEntity implements Serializable {
  @Id
  @Column(name = "ad_id")
  private Integer adId;

  @Id
  @Column(name = "instrument_id")
  private Integer instrumentId;
}
