package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

@Getter
@Setter
@ToString
@Entity
@Table(name = "email_confirmation")
public class EmailConfirmationEntity extends AbstractPersistable<Integer> {

  private String email;
  @ToString.Exclude
  private String password;

  @Type(type = "uuid-char")
  @Column(name = "token_uuid")
  private UUID token;

  @Column(name = "token_expiration")
  private LocalDateTime tokenExpiration;

  private boolean confirmed;

  public boolean isTokenExpired() {
    return tokenExpiration.isBefore(LocalDateTime.now());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmailConfirmationEntity that = (EmailConfirmationEntity) o;
    return Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email);
  }
}
