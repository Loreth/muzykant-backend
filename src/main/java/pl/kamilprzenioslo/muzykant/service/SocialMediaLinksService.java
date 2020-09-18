package pl.kamilprzenioslo.muzykant.service;

import pl.kamilprzenioslo.muzykant.dtos.SocialMediaLinks;
import pl.kamilprzenioslo.muzykant.persistance.entities.SocialMediaLinksEntity;

public interface SocialMediaLinksService
    extends CrudService<SocialMediaLinks, Integer>,
        SpecificationService<SocialMediaLinks, SocialMediaLinksEntity, Integer> {}
