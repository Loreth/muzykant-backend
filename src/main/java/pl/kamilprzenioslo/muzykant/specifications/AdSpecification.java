package pl.kamilprzenioslo.muzykant.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.persistance.entities.AdEntity;

@And({
  @Spec(
      path = "publishedDate",
      params = {"publishedDateAfterInclusive", "publishedDateBeforeInclusive"},
      spec = Between.class),
  @Spec(path = "location", spec = LikeIgnoreCase.class),
  @Spec(path = "commercial", spec = Equal.class),
  @Spec(path = "userId", params = "userId", spec = Equal.class),
  @Spec(path = "user.userType", params = "userType", spec = In.class)
})
public interface AdSpecification<T extends AdEntity> extends Specification<T> {}
