package pl.kamilprzenioslo.muzykant.controllers;

import java.io.Serializable;
import java.net.URI;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;
import pl.kamilprzenioslo.muzykant.dtos.IdentifiableDto;
import pl.kamilprzenioslo.muzykant.service.CrudService;
import pl.kamilprzenioslo.muzykant.validation.OnCreate;
import pl.kamilprzenioslo.muzykant.validation.OnPut;

@RestController
@Validated
public abstract class BaseRestController<T extends IdentifiableDto<ID>, ID extends Serializable>
    extends BaseRestGetController<T, ID> {

  protected static final String ENTITY_EXISTS_EXCEPTION_MSG = "Entity already exists with id=";
  protected static final String ENTITY_NOT_FOUND_EXCEPTION_MSG = "Entity not found with id=";

  private final CrudService<T, ID> service;

  public BaseRestController(CrudService<T, ID> service) {
    super(service);
    this.service = service;
  }

  @Validated(OnCreate.class)
  @PostMapping
  public ResponseEntity<T> create(@Valid @RequestBody T dto, HttpServletRequest request) {
    if (dto.getId() != null && service.existsById(dto.getId())) {
      throw new EntityExistsException(ENTITY_EXISTS_EXCEPTION_MSG + dto.getId());
    }

    T savedEntity = service.save(dto);
    final URI entityMapping =
        new UriTemplate(request.getRequestURI() + RestMappings.ID).expand(savedEntity.getId());

    return ResponseEntity.created(entityMapping).body(savedEntity);
  }

  @DeleteMapping(RestMappings.ID)
  public void deleteById(@PathVariable ID id) {
    if (!service.existsById(id)) {
      throw new EntityNotFoundException(ENTITY_NOT_FOUND_EXCEPTION_MSG + id);
    }

    service.deleteById(id);
  }

  @Validated(OnPut.class)
  @PutMapping(RestMappings.ID)
  public ResponseEntity<T> updateById(@Valid @RequestBody T dto, @PathVariable ID id) {
    T responseDto;

    if (service.existsById(id)) {
      dto.setId(id);
      responseDto = service.save(dto);
    } else {
      throw new EntityNotFoundException(NOT_FOUND_EXCEPTION_MSG + id);
    }

    return ResponseEntity.ok(responseDto);
  }
}
