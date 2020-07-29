package pl.kamilprzenioslo.muzykant.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.JamSessionAdEntity;

@Spec(path = "test", params = "test", spec = Like.class)
public interface JamSessionAdSpecification extends Specification<JamSessionAdEntity> {}
