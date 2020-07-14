package pl.kamilprzenioslo.muzykant.dtos;

import lombok.Data;

@Data
public class Band extends User {
  private String name;
  private Short formationYear;
}
