package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "social_media_links")
public class SocialMediaLinksEntity implements Persistable<Integer> {

  @Id private Integer id;
  private String youtube;
  private String soundcloud;
  private String webpage;
  @Version private Integer version;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_profile_id")
  @MapsId
  private UserEntity user;

  @Override
  public boolean isNew() {
    return version == null;
  }
}
