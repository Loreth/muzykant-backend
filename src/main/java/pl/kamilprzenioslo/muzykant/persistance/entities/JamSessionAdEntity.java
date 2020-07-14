package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "jam_session_ad")
public class JamSessionAdEntity extends AdEntity {

  private String location;
}
