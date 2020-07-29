package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.MusicianWantedAd;
import pl.kamilprzenioslo.muzykant.persistance.entities.MusicianWantedAdEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.MusicianWantedAdRepository;
import pl.kamilprzenioslo.muzykant.service.MusicianWantedAdService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class MusicianWantedAdServiceImpl
    extends BaseSpecificationCrudService<
        MusicianWantedAd, MusicianWantedAdEntity, Integer, MusicianWantedAdRepository>
    implements MusicianWantedAdService {

  public MusicianWantedAdServiceImpl(
      MusicianWantedAdRepository repository,
      BaseMapper<MusicianWantedAd, MusicianWantedAdEntity> mapper) {
    super(repository, mapper);
  }
}
