package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.VocalTechnique;
import pl.kamilprzenioslo.muzykant.persistance.entities.VocalTechniqueEntity;

public interface VocalTechniqueService
    extends CrudService<VocalTechnique, Integer>,
        SpecificationService<VocalTechnique, VocalTechniqueEntity, Integer> {}
