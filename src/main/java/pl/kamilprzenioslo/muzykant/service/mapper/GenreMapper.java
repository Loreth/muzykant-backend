package pl.kamilprzenioslo.muzykant.service.mapper;

import org.mapstruct.Mapper;
import pl.kamilprzenioslo.muzykant.dtos.Genre;
import pl.kamilprzenioslo.muzykant.persistance.entities.GenreEntity;

@Mapper
public interface GenreMapper extends BaseMapper<Genre, GenreEntity> {}
