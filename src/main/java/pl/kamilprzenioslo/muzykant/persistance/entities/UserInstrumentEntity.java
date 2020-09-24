package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_instrument")
public class UserInstrumentEntity implements Serializable {

  @Id
  @Column(name = "user_profile_id")
  private Integer userId;

  @Id
  @Column(name = "instrument_id")
  private Integer instrumentId;
}
