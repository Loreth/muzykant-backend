package pl.kamilprzenioslo.muzykant.controllers;

/** API resource paths */
public final class RestMappings {

  public static final String ID = "/{id}";
  public static final String SEARCH = "/search";
  public static final String SIGN_UP = "/sign-up";
  public static final String CONFIRM_EMAIL = "/confirm-email";

  public static final String BAND = "/bands";
  public static final String MUSICIAN = "/musicians";
  public static final String REGULAR_USER = "/regular-users";
  public static final String BAND_WANTED_AD = "/band-wanted-ads";
  public static final String MUSICIAN_WANTED_AD = "/musician-wanted-ads";
  public static final String JAM_SESSION_AD = "/jam-session-ads";
  public static final String INSTRUMENT = "/instruments";
  public static final String GENRE = "/genres";
  public static final String EQUIPMENT = "/equipments";
  public static final String USER_IMAGE = "/user-images";
  public static final String PREDEFINED_VOCAL_RANGE = "/predefined-vocal-ranges";
  public static final String VOCAL_TECHNIQUE = "/vocal-techniques";
  public static final String VOIVODESHIP = "/voivodeships";

  public static final String IMAGE_UPLOAD = "/upload";
  public static final String IMAGE_UPLOADS = "/image-uploads";

  private RestMappings() {}
}
