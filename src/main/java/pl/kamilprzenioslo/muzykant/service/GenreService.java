package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.Genre;
import pl.kamilprzenioslo.muzykant.persistance.entities.GenreEntity;

public interface GenreService
    extends CrudService<Genre, Integer>, SpecificationService<Genre, GenreEntity, Integer> {}
