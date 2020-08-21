package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import pl.kamilprzenioslo.muzykant.persistance.AdType.Values;

@Entity
@Table(name = "band_wanted_ad")
@DiscriminatorValue(Values.BAND_WANTED)
@PrimaryKeyJoinColumn(name = "ad_id")
public class BandWantedAdEntity extends AdEntity {}
