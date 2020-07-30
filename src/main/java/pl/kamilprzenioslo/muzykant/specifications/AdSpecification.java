package pl.kamilprzenioslo.muzykant.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.Specification;

@And({
  @Spec(
      path = "publishedDate",
      params = {"publishedDateAfterInclusive", "publishedDateBeforeInclusive"},
      spec = Between.class),
  @Spec(path = "location", spec = LikeIgnoreCase.class),
  @Spec(path = "commercial", spec = Equal.class),
  @Spec(path = "user.id", params = "userId", spec = Equal.class)
})
public interface AdSpecification<T extends Persistable<?>> extends Specification<T> {}
