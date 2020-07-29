package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.Genre;
import pl.kamilprzenioslo.muzykant.persistance.entities.GenreEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.GenreRepository;
import pl.kamilprzenioslo.muzykant.service.GenreService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class GenreServiceImpl
    extends BaseSpecificationCrudService<Genre, GenreEntity, Integer, GenreRepository>
    implements GenreService {

  public GenreServiceImpl(GenreRepository repository, BaseMapper<Genre, GenreEntity> mapper) {
    super(repository, mapper);
  }
}
