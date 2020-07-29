package pl.kamilprzenioslo.muzykant.persistance.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "jam_session_ad")
@PrimaryKeyJoinColumn(name = "ad_id")
public class JamSessionAdEntity extends AdEntity {}
