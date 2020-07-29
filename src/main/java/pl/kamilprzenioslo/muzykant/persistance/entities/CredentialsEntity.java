package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "credentials")
public class CredentialsEntity extends AbstractPersistable<Integer> {

  private String email;
  private String username;
  private String password;
}
