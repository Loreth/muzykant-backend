package pl.kamilprzenioslo.muzykant.dtos;

import java.io.Serializable;

public interface IdentifiableDto<ID extends Serializable> {

  ID getId();

  void setId(ID id);
}
