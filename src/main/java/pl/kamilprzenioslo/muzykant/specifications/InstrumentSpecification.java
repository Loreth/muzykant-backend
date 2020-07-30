package pl.kamilprzenioslo.muzykant.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.InstrumentEntity;

@Spec(path = "name", spec = LikeIgnoreCase.class)
public interface InstrumentSpecification extends Specification<InstrumentEntity> {}
