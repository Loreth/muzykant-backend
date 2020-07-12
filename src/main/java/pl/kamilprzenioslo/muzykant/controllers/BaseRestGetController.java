package pl.kamilprzenioslo.muzykant.controllers;

import java.io.Serializable;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.kamilprzenioslo.muzykant.dtos.IdentifiableDto;
import pl.kamilprzenioslo.muzykant.service.ReadService;

@RequiredArgsConstructor
@RestController
public abstract class BaseRestGetController<
    T extends IdentifiableDto<ID>, ID extends Serializable> {

  protected static final String NOT_FOUND_EXCEPTION_MSG = "Entity not found with id=";

  private final ReadService<T, ID> service;

  @GetMapping
  public Page<T> getAll(Pageable pageable) {
    return service.findAll(pageable);
  }

  @GetMapping(RestMappings.ID)
  public T getById(@PathVariable ID id) {
    return service
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_EXCEPTION_MSG + id));
  }
}
