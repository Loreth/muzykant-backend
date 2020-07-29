package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.Band;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.BandRepository;
import pl.kamilprzenioslo.muzykant.service.BandService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class BandServiceImpl
    extends BaseSpecificationCrudService<Band, BandEntity, Integer, BandRepository>
    implements BandService {

  public BandServiceImpl(BandRepository repository, BaseMapper<Band, BandEntity> mapper) {
    super(repository, mapper);
  }
}
