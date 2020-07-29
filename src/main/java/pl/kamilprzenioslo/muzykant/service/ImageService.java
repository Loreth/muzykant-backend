package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.Image;
import pl.kamilprzenioslo.muzykant.persistance.entities.ImageEntity;

public interface ImageService
    extends CrudService<Image, Integer>, SpecificationService<Image, ImageEntity, Integer> {}
