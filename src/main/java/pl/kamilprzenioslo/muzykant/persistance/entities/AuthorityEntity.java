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
import org.springframework.security.core.GrantedAuthority;
import pl.kamilprzenioslo.muzykant.security.UserAuthority;

@Getter
@Setter
@ToString
@Entity
@Table(name = "authority")
public class AuthorityEntity extends AbstractPersistable<Integer> implements GrantedAuthority {

  @Enumerated(EnumType.STRING)
  @Column(name = "name")
  private UserAuthority userAuthority;

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
    AuthorityEntity that = (AuthorityEntity) o;
    return userAuthority.equals(that.userAuthority);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), userAuthority);
  }

  public String getAuthority() {
    return userAuthority.name();
  }
}
