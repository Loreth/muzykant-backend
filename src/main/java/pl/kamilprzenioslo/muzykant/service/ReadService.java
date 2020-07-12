package pl.kamilprzenioslo.muzykant.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadService<T, ID> {

  Page<T> findAll(Pageable pageable);

  List<T> findAll();

  boolean existsById(ID id);

  Optional<T> findById(ID id);

  long count();
}
