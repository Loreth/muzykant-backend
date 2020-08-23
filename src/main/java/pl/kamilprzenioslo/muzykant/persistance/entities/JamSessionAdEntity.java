package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import pl.kamilprzenioslo.muzykant.persistance.enums.AdType.Values;

@Entity
@Table(name = "jam_session_ad")
@DiscriminatorValue(Values.JAM_SESSION)
@PrimaryKeyJoinColumn(name = "ad_id")
public class JamSessionAdEntity extends AdEntity {}
