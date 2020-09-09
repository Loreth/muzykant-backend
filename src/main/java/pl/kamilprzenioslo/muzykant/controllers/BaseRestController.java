package pl.kamilprzenioslo.muzykant.controllers;

import java.io.Serializable;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriTemplate;
import pl.kamilprzenioslo.muzykant.dtos.IdentifiableDto;
import pl.kamilprzenioslo.muzykant.service.CrudService;
import pl.kamilprzenioslo.muzykant.validation.OnPost;
import pl.kamilprzenioslo.muzykant.validation.OnPut;

@RestController
@Validated
public abstract class BaseRestController<T extends IdentifiableDto<ID>, ID extends Serializable>
    extends GetController<T, ID> {

  protected static final String ENTITY_EXISTS = "Entity already exists with id=";
  protected static final String ENTITY_NOT_FOUND = "Entity not found with id=";
  protected static final String CREATE_USING_POST_INSTEAD =
      "To create resource use POST under resource path (without id) instead";
  protected static final String ENTITY_ID_DIFFERENT_FROM_PATH_VARIABLE_ID =
      "Entity sent in body has a different id than id used in the request's path";

  private final CrudService<T, ID> service;

  public BaseRestController(CrudService<T, ID> service) {
    super(service);
    this.service = service;
  }

  @Validated(OnPost.class)
  @PostMapping
  public ResponseEntity<T> create(@Valid @RequestBody T dto, HttpServletRequest request) {
    if (dto.getId() != null && service.existsById(dto.getId())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, ENTITY_EXISTS + dto.getId());
    }

    T savedEntity = service.save(dto);
    final URI entityMapping =
        new UriTemplate(request.getRequestURI() + RestMappings.ID).expand(savedEntity.getId());

    return ResponseEntity.created(entityMapping)
        .contentType(MediaType.APPLICATION_JSON)
        .body(savedEntity);
  }

  @DeleteMapping(RestMappings.ID)
  public void deleteById(@PathVariable ID id) {
    if (!service.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ENTITY_NOT_FOUND + id);
    }

    service.deleteById(id);
  }

  @Validated(OnPut.class)
  @PutMapping(RestMappings.ID)
  public T updateById(@Valid @RequestBody T dto, @PathVariable ID id) {
    verifyPutRequest(dto, id);

    return service.save(dto);
  }

  private void verifyPutRequest(T dto, ID pathId) {
    if (!service.existsById(pathId)) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, ENTITY_NOT_FOUND + pathId + "." + CREATE_USING_POST_INSTEAD);
    } else if (!dto.getId().equals(pathId)) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, ENTITY_ID_DIFFERENT_FROM_PATH_VARIABLE_ID);
    }
  }
}
