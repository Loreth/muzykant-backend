package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_vocal_technique")
public class UserVocalTechniqueEntity implements Serializable {

  @Id
  @Column(name = "user_profile_id")
  private Integer userId;

  @Id
  @Column(name = "vocal_technique_id")
  private Integer vocalTechniqueId;
}
