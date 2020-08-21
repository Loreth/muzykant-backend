package pl.kamilprzenioslo.muzykant.persistance;

public enum UserType {
  BAND(Values.BAND),
  MUSICIAN(Values.MUSICIAN),
  REGULAR(Values.REGULAR);

  private final String value;

  UserType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
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
