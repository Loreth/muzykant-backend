package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.Voivodeship;
import pl.kamilprzenioslo.muzykant.persistance.entities.VoivodeshipEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.VoivodeshipRepository;
import pl.kamilprzenioslo.muzykant.service.VoivodeshipService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class VoivodeshipServiceImpl
    extends BaseReadService<Voivodeship, VoivodeshipEntity, Integer, VoivodeshipRepository>
    implements VoivodeshipService {

  public VoivodeshipServiceImpl(
      VoivodeshipRepository repository, BaseMapper<Voivodeship, VoivodeshipEntity> mapper) {
    super(repository, mapper);
  }
}
