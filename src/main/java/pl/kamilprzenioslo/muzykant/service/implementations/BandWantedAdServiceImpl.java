package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.BandWantedAd;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandWantedAdEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.BandWantedAdRepository;
import pl.kamilprzenioslo.muzykant.service.BandWantedAdService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class BandWantedAdServiceImpl
    extends BaseSpecificationCrudService<
        BandWantedAd, BandWantedAdEntity, Integer, BandWantedAdRepository>
    implements BandWantedAdService {

  public BandWantedAdServiceImpl(
      BandWantedAdRepository repository, BaseMapper<BandWantedAd, BandWantedAdEntity> mapper) {
    super(repository, mapper);
  }
}
