package pl.kamilprzenioslo.muzykant.service;

import java.io.Serializable;
import java.util.Optional;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.IdentifiableDto;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public abstract class BaseCrudService<
        T extends IdentifiableDto<ID>,
        E extends Persistable<ID>,
        ID extends Serializable,
        R extends JpaRepository<E, ID>>
    extends BaseReadService<T, E, ID, R> implements CrudService<T, ID> {

  public BaseCrudService(R repository, BaseMapper<T, E> mapper) {
    super(repository, mapper);
  }

  @Override
  public T save(T dto) {
    E savedEntity = repository.save(mapper.mapToEntity(dto));

    return mapper.mapToDto(savedEntity);
  }

  @Override
  public void deleteById(ID id) {
    repository.deleteById(id);
  }

  @Override
  public Optional<T> updateById(T dto, ID id) {
    Optional<E> entityOptional = repository.findById(id);

    return entityOptional.map(
        entity -> {
          dto.setId(id);
          repository.save(mapper.mapToEntity(dto));
          return mapper.mapToDto(entity);
        });
  }

  @Override
  public void deleteAll() {
    repository.deleteAll();
  }
}
