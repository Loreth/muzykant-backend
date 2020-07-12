package pl.kamilprzenioslo.muzykant.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;
import pl.kamilprzenioslo.muzykant.validation.OnCreate;

@Data
@JsonPropertyOrder({"id"})
public abstract class BaseInheritedIdDto<ID extends Serializable> implements IdentifiableDto<ID> {

  @NotNull(groups = {OnCreate.class})
  private ID id;
}
