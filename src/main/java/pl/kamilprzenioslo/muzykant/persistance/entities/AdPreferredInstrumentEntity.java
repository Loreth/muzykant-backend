package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
