package pl.kamilprzenioslo.muzykant.service;

import java.util.Optional;

public interface CrudService<T, ID> extends ReadService<T, ID> {

  T save(T dto);

  void deleteById(ID id);

  Optional<T> updateById(T dto, ID id);

  void deleteAll();
}
