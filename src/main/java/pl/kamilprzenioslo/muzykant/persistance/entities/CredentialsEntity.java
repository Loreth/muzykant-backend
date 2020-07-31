package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.kamilprzenioslo.muzykant.persistance.UserType;

@Getter
@Setter
@ToString
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    CredentialsEntity that = (CredentialsEntity) o;
    return email.equals(that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), email);
  }
}
