package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Social_media_links")
public class SocialMediaLinksEntity implements Persistable<Integer> {

  @Id private Integer id;
  private String youtube;
  private String soundcloud;
  private String webpage;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  private UserEntity user;

  @Override
  public boolean isNew() {
    return getId() == null;
  }
}
