package pl.kamilprzenioslo.muzykant.service;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.Specification;
import pl.kamilprzenioslo.muzykant.dtos.IdentifiableDto;

public interface SpecificationService<
    T extends IdentifiableDto<ID>, U extends Persistable<ID>, ID extends Serializable> {

  Page<T> findAll(Specification<U> specification, Pageable pageable);

  Page<T> findAll(List<Specification<U>> specifications, Pageable pageable);
}
