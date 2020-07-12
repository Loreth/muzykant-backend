package pl.kamilprzenioslo.muzykant.service;

import java.io.Serializable;
import org.springframework.data.domain.Persistable;
import pl.kamilprzenioslo.muzykant.dtos.IdentifiableDto;

public interface SpecificationCrudService<
        T extends IdentifiableDto<ID>, U extends Persistable<ID>, ID extends Serializable>
    extends CrudService<T, ID>, SpecificationReadService<T, U, ID> {}
