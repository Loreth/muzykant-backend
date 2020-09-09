package pl.kamilprzenioslo.muzykant.persistance.enums;

import pl.kamilprzenioslo.muzykant.security.UserAuthority;

public enum UserType {
  BAND(Values.BAND, UserAuthority.ROLE_BAND),
  MUSICIAN(Values.MUSICIAN, UserAuthority.ROLE_MUSICIAN),
  REGULAR(Values.REGULAR, UserAuthority.ROLE_REGULAR_USER);

  private final String value;
  private final UserAuthority userAuthority;

  UserType(String value, UserAuthority userAuthority) {
    this.value = value;
    this.userAuthority = userAuthority;
  }

  public String getValue() {
    return value;
  }

  public UserAuthority getUserAuthority() {
    return userAuthority;
  }

  // workaround for annotations being unable to use enums. They can use values from this class
  // instead
  public static class Values {
    public static final String BAND = "BAND";
    public static final String MUSICIAN = "MUSICIAN";
    public static final String REGULAR = "REGULAR";

    private Values() {}
  }
}
