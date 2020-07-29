package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.PredefinedVocalRange;
import pl.kamilprzenioslo.muzykant.persistance.entities.PredefinedVocalRangeEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.PredefinedVocalRangeRepository;
import pl.kamilprzenioslo.muzykant.service.PredefinedVocalRangeService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class PredefinedVocalRangeServiceImpl
    extends BaseSpecificationCrudService<
        PredefinedVocalRange, PredefinedVocalRangeEntity, Integer, PredefinedVocalRangeRepository>
    implements PredefinedVocalRangeService {

  public PredefinedVocalRangeServiceImpl(
      PredefinedVocalRangeRepository repository,
      BaseMapper<PredefinedVocalRange, PredefinedVocalRangeEntity> mapper) {
    super(repository, mapper);
  }
}
