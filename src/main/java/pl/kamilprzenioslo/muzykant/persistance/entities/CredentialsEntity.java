package pl.kamilprzenioslo.muzykant.persistance.entities;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "credentials")
public class CredentialsEntity extends AbstractPersistable<Integer> {

  private String email;
  @ToString.Exclude private String password;

  @OneToOne
  @JoinColumn(name = "user_profile_id")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "authority_id")
  private AuthorityEntity authority;

  @OneToOne(
      mappedBy = "credentials",
      orphanRemoval = true,
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  private EmailConfirmationEntity emailConfirmation;

  public void setEmailConfirmation(EmailConfirmationEntity emailConfirmation) {
    if (emailConfirmation == null) {
      if (this.emailConfirmation != null) {
        this.emailConfirmation.setCredentials(null);
      }
    } else {
      emailConfirmation.setCredentials(this);
    }
    this.emailConfirmation = emailConfirmation;
  }

  public boolean isEmailConfirmed() {
    return emailConfirmation == null;
  }

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
