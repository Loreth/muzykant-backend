package pl.kamilprzenioslo.muzykant.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.kamilprzenioslo.muzykant.dtos.Band;
import pl.kamilprzenioslo.muzykant.dtos.Genre;
import pl.kamilprzenioslo.muzykant.persistance.entities.BandEntity;
import pl.kamilprzenioslo.muzykant.persistance.entities.GenreEntity;

class BandMapperTest {

  @Test
  void givenDto_whenMapToEntity_thenMapsProperly() {
    BandMapper bandMapper = Mappers.getMapper(BandMapper.class);

    Band band = new Band("Name", (short) 1997);
    band.setId(1);
    band.setCity("City");
    band.setDescription("desc");
    band.setPhone("123123123");
    Genre genre1 = new Genre("genre1");
    genre1.setId(1);
    Genre genre2 = new Genre("genre2");
    band.setGenres(Set.of(genre1, genre2));

    BandEntity actualBandEntity = bandMapper.mapToEntity(band);

    assertEquals("Name", actualBandEntity.getName());
    assertEquals((short) 1997, actualBandEntity.getFormationYear());
    assertEquals(1, actualBandEntity.getId());
    assertEquals("City", actualBandEntity.getCity());
    assertEquals("desc", actualBandEntity.getDescription());
    assertEquals("123123123", actualBandEntity.getPhone());

    GenreEntity genreEntity1 = new GenreEntity();
    genreEntity1.setName("genre1");
    genreEntity1.setId(1);
    GenreEntity genreEntity2 = new GenreEntity();
    genreEntity2.setName("genre2");
    assertThat(actualBandEntity.getGenres()).containsExactly(genreEntity1, genreEntity2);
  }
}
