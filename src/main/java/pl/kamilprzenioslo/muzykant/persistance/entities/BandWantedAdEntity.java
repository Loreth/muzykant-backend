package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "band_wanted_ad")
@PrimaryKeyJoinColumn(name = "ad_id")
public class BandWantedAdEntity extends AdEntity {}
