package pl.kamilprzenioslo.muzykant.controllers.mappings;

/** API resource paths */
public final class RestMappings {

  public static final String ID = "/{id}";
  public static final String SEARCH = "/search";
  public static final String SIGN_UP = "/sign-up";
  public static final String CONFIRM_EMAIL = "/confirm-email";
  public static final String RESEND_MAIL = "/resend-mail";

  public static final String USER = "/users";
  public static final String CHANGE_USER_PASSWORD = USER + ID + "/change-password";
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
  public static final String VOIVODESHIP = "/voivodeships";
  public static final String SOCIAL_MEDIA_LINKS = "/social-media-links";
  public static final String CHAT_MESSAGES = "/chat-messages";
  public static final String CONVERSATIONS = "/conversations";

  public static final String IMAGE_UPLOAD = "/upload";
  public static final String IMAGE_UPLOADS = "/image-uploads";

  private RestMappings() {}
}
