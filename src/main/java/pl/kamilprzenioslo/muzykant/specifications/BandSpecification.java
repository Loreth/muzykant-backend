package pl.kamilprzenioslo.muzykant.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandEntity;

@Spec(path = "name", spec = LikeIgnoreCase.class)
public interface BandSpecification extends UserSpecification<BandEntity> {}
