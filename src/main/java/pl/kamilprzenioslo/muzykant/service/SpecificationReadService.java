package pl.kamilprzenioslo.muzykant.service;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.dtos.IdentifiableDto;

public interface SpecificationReadService<
        T extends IdentifiableDto<ID>, U extends Persistable<ID>, ID extends Serializable>
    extends ReadService<T, ID> {

  List<T> findAll(Specification<U> specification);

  Page<T> findAll(Specification<U> specification, Pageable pageable);

  List<T> findAll(Specification<U> specification, Sort sort);
}
