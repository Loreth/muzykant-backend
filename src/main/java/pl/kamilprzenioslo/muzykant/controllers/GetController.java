package pl.kamilprzenioslo.muzykant.controllers;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.kamilprzenioslo.muzykant.controllers.mappings.RestMappings;
import pl.kamilprzenioslo.muzykant.dtos.IdentifiableDto;
import pl.kamilprzenioslo.muzykant.service.ReadService;

@RequiredArgsConstructor
@RestController
public abstract class GetController<T extends IdentifiableDto<ID>, ID extends Serializable> {

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
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_EXCEPTION_MSG + id));
  }
}
