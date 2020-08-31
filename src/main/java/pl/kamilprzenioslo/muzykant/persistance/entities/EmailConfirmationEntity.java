package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "email_confirmation")
public class EmailConfirmationEntity implements Persistable<Integer> {

  @Id
  private Integer id;

  @Type(type = "uuid-char")
  @Column(name = "token_uuid")
  private UUID token;

  @Column(name = "token_expiration")
  private LocalDateTime tokenExpiration;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  private CredentialsEntity credentials;

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
    return Objects.equals(token, that.token);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token);
  }

  @Override
  public boolean isNew() {
    return getId() == null;
  }
}
