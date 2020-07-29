package pl.kamilprzenioslo.muzykant.controllers;

import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.IdentifiableDto;
import pl.kamilprzenioslo.muzykant.service.CrudService;
import pl.kamilprzenioslo.muzykant.service.SpecificationService;

@RestController
public abstract class SpecificationRestController<
        T extends IdentifiableDto<ID>,
        E extends Persistable<ID>,
        ID extends Serializable,
        S extends Specification<E>,
        U extends CrudService<T, ID> & SpecificationService<T, E, ID>>
    extends BaseRestController<T, ID> {

  private final U service;

  public SpecificationRestController(U service) {
    super(service);
    this.service = service;
  }

  @GetMapping(RestMappings.SEARCH)
  public Page<T> getAllWithGivenParameters(S specification, Pageable pageable) {
    return service.findAll(specification, pageable);
  }
}
