package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.VocalTechnique;
import pl.kamilprzenioslo.muzykant.persistance.entities.VocalTechniqueEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.VocalTechniqueRepository;
import pl.kamilprzenioslo.muzykant.service.VocalTechniqueService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class VocalTechniqueServiceImpl
    extends BaseSpecificationCrudService<
        VocalTechnique, VocalTechniqueEntity, Integer, VocalTechniqueRepository>
    implements VocalTechniqueService {

  public VocalTechniqueServiceImpl(
      VocalTechniqueRepository repository,
      BaseMapper<VocalTechnique, VocalTechniqueEntity> mapper) {
    super(repository, mapper);
  }
}
