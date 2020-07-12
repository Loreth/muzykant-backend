package pl.kamilprzenioslo.muzykant.service.mapper;

import java.util.Collection;
import java.util.List;

public interface BaseMapper<D, E> {

  D mapToDto(E entity);

  E mapToEntity(D dto);

  List<D> mapToDtoList(Collection<E> entity);

  List<E> mapToEntityList(Collection<D> dto);
}
