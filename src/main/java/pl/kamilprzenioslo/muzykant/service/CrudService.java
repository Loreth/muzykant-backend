package pl.kamilprzenioslo.muzykant.service;

public interface CrudService<T, ID> extends ReadService<T, ID> {

  T save(T dto);

  void deleteById(ID id);

  void deleteAll();
}
