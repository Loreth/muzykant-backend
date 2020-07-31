package pl.kamilprzenioslo.muzykant.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import javax.validation.constraints.Null;
import lombok.Data;
import pl.kamilprzenioslo.muzykant.validation.OnPost;

@Data
@JsonPropertyOrder({"id"})
public abstract class BaseDto<ID extends Serializable> implements IdentifiableDto<ID> {

  @Null(groups = OnPost.class)
  private ID id;
}
