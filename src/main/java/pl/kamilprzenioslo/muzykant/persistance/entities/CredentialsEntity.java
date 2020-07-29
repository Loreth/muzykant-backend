package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kamilprzenioslo.muzykant.persistance.UserType;

@Data
@NoArgsConstructor
@Entity
@Table(name = "credentials")
public class CredentialsEntity extends AbstractPersistable<Integer> {

  private String email;
  private String password;

  @Column(name = "user_id")
  private int userId;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_type")
  private UserType userType;
}
