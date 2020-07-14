package pl.kamilprzenioslo.muzykant.dtos;

import lombok.Data;

@Data
public class RegularUser extends User {

  private Person person;
}
