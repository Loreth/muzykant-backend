package pl.kamilprzenioslo.muzykant.persistance;

public enum AdType {
  BAND_WANTED(Values.BAND_WANTED),
  MUSICIAN_WANTED(Values.MUSICIAN_WANTED),
  JAM_SESSION(Values.JAM_SESSION);

  private final String value;

  AdType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  // workaround for annotations being unable to use enums. They can use values from this class
  // instead
  public static class Values {
    public static final String BAND_WANTED = "BAND_WANTED";
    public static final String MUSICIAN_WANTED = "MUSICIAN_WANTED";
    public static final String JAM_SESSION = "JAM_SESSION";

    private Values() {}
  }
}
