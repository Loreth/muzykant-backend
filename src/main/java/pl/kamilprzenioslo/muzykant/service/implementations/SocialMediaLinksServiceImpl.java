package pl.kamilprzenioslo.muzykant.service.implementations;

import org.springframework.stereotype.Service;
import pl.kamilprzenioslo.muzykant.dtos.SocialMediaLinks;
import pl.kamilprzenioslo.muzykant.persistance.entities.SocialMediaLinksEntity;
import pl.kamilprzenioslo.muzykant.persistance.repositories.SocialMediaLinksRepository;
import pl.kamilprzenioslo.muzykant.service.SocialMediaLinksService;
import pl.kamilprzenioslo.muzykant.service.mapper.BaseMapper;

@Service
public class SocialMediaLinksServiceImpl
    extends BaseSpecificationCrudService<
        SocialMediaLinks, SocialMediaLinksEntity, Integer, SocialMediaLinksRepository>
    implements SocialMediaLinksService {

  public SocialMediaLinksServiceImpl(
      SocialMediaLinksRepository repository,
      BaseMapper<SocialMediaLinks, SocialMediaLinksEntity> mapper) {
    super(repository, mapper);
  }
}
