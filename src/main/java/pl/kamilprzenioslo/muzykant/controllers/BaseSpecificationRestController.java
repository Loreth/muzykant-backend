package pl.kamilprzenioslo.muzykant.controllers;

import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.IdentifiableDto;
import pl.kamilprzenioslo.muzykant.service.SpecificationCrudService;

@RestController
public abstract class BaseSpecificationRestController<
        T extends IdentifiableDto<ID>,
        ID extends Serializable,
        E extends Persistable<ID>,
        S extends Specification<E>>
    extends BaseRestController<T, ID> {

  private final SpecificationCrudService<T, E, ID> service;

  public BaseSpecificationRestController(SpecificationCrudService<T, E, ID> service) {
    super(service);
    this.service = service;
  }

  @GetMapping(RestMappings.SEARCH)
  public Page<T> getAllWithGivenParameters(S specification, Pageable pageable) {
    return service.findAll(specification, pageable);
  }
}
