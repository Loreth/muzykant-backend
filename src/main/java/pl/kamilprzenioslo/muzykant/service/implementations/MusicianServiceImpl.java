package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.Musician;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.MusicianRepository;
import pl.kamilprzenioslo.muzykant.service.MusicianService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class MusicianServiceImpl
    extends BaseSpecificationCrudService<Musician, MusicianEntity, Integer, MusicianRepository>
    implements MusicianService {

  public MusicianServiceImpl(
      MusicianRepository repository, BaseMapper<Musician, MusicianEntity> mapper) {
    super(repository, mapper);
  }
}
