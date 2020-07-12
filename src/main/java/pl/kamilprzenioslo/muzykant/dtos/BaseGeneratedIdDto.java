package pl.kamilprzenioslo.muzykant.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import javax.validation.constraints.Null;
import lombok.Data;
import pl.kamilprzenioslo.muzykant.validation.OnCreate;

@Data
@JsonPropertyOrder({"id"})
public abstract class BaseGeneratedIdDto<ID extends Serializable> implements IdentifiableDto<ID> {

  @Null(groups = {OnCreate.class})
  private ID id;
}
