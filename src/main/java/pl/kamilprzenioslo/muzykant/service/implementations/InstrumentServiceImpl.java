package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.Instrument;
import pl.kamilprzenioslo.muzykant.persistance.entities.InstrumentEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.InstrumentRepository;
import pl.kamilprzenioslo.muzykant.service.InstrumentService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class InstrumentServiceImpl
    extends BaseSpecificationCrudService<
        Instrument, InstrumentEntity, Integer, InstrumentRepository>
    implements InstrumentService {

  public InstrumentServiceImpl(
      InstrumentRepository repository, BaseMapper<Instrument, InstrumentEntity> mapper) {
    super(repository, mapper);
  }
}
