package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.JamSessionAd;
import pl.kamilprzenioslo.muzykant.persistance.entities.JamSessionAdEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.JamSessionAdRepository;
import pl.kamilprzenioslo.muzykant.service.JamSessionAdService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class JamSessionAdServiceImpl
    extends BaseSpecificationCrudService<
        JamSessionAd, JamSessionAdEntity, Integer, JamSessionAdRepository>
    implements JamSessionAdService {

  public JamSessionAdServiceImpl(
      JamSessionAdRepository repository, BaseMapper<JamSessionAd, JamSessionAdEntity> mapper) {
    super(repository, mapper);
  }
}
