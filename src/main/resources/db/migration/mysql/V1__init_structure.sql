-- strong entities
CREATE TABLE Voivodeship
(
    id   int         NOT NULL AUTO_INCREMENT,
    name varchar(30) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Person
(
    id         int         NOT NULL AUTO_INCREMENT,
    first_name varchar(50) NOT NULL,
    last_name  varchar(50) NOT NULL,
    pseudo     varchar(30),
    gender     varchar(1)  NOT NULL,
    birthdate  date        NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Genre
(
    id   int         NOT NULL AUTO_INCREMENT,
    name varchar(30) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Instrument
(
    id   int         NOT NULL AUTO_INCREMENT,
    name varchar(40) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Authority
(
    id   int         NOT NULL AUTO_INCREMENT,
    name varchar(30) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

-- weak entities
CREATE TABLE User_profile
(
    id                 int         NOT NULL AUTO_INCREMENT,
    user_type          varchar(30) NOT NULL,
    link_name          varchar(30) NOT NULL UNIQUE,
    description        varchar(1000),
    phone              varchar(60),
    city               varchar(60),
    voivodeship_id     int         NOT NULL,
    profile_image_link varchar(1000),
    PRIMARY KEY (id),
    CONSTRAINT FK_User_profile_Voivodeship FOREIGN KEY (voivodeship_id) REFERENCES Voivodeship (id)
);
CREATE TABLE Credentials
(
    id              int          NOT NULL AUTO_INCREMENT,
    email           varchar(255) NOT NULL UNIQUE,
    password        varchar(60)  NOT NULL,
    authority_id    int,
    user_profile_id int,
    PRIMARY KEY (id),
    CONSTRAINT FK_Credentials_Authority FOREIGN KEY (authority_id) REFERENCES Authority (id),
    CONSTRAINT FK_Credentials_User_profile FOREIGN KEY (user_profile_id) REFERENCES User_profile (id) ON DELETE CASCADE
);
CREATE TABLE Email_confirmation
(
    credentials_id   int         NOT NULL AUTO_INCREMENT,
    token_uuid       varchar(36) NOT NULL,
    token_expiration datetime    NOT NULL,
    PRIMARY KEY (credentials_id),
    CONSTRAINT FK_Email_confirmation_Credentials FOREIGN KEY (credentials_id) REFERENCES Credentials (id) ON DELETE CASCADE
);
CREATE TABLE Band
(
    user_profile_id int          NOT NULL,
    name            varchar(100) NOT NULL,
    formation_year  smallint,
    PRIMARY KEY (user_profile_id),
    CONSTRAINT FK_Band_User_profile FOREIGN KEY (user_profile_id) REFERENCES User_profile (id) ON DELETE CASCADE
);
CREATE TABLE Musician
(
    user_profile_id int NOT NULL,
    person_id       int NOT NULL,
    PRIMARY KEY (user_profile_id),
    CONSTRAINT FK_Musician_User_profile FOREIGN KEY (user_profile_id) REFERENCES User_profile (id) ON DELETE CASCADE,
    CONSTRAINT FK_Musician_Person FOREIGN KEY (person_id) REFERENCES Person (id)
);
CREATE TABLE Regular_user
(
    user_profile_id int NOT NULL,
    person_id       int NOT NULL,
    PRIMARY KEY (user_profile_id),
    CONSTRAINT FK_Regular_user_User_profile FOREIGN KEY (user_profile_id) REFERENCES User_profile (id) ON DELETE CASCADE,
    CONSTRAINT FK_Regular_user_Person FOREIGN KEY (person_id) REFERENCES Person (id)
);
CREATE TABLE Ad
(
    id              int          NOT NULL AUTO_INCREMENT,
    ad_type         varchar(30)  NOT NULL,
    published_date  date                  DEFAULT (curdate()),
    location        varchar(255) NOT NULL,
    description     varchar(1000),
    commercial      bool         NOT NULL DEFAULT FALSE,
    user_profile_id int          NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_Ad_User_profile FOREIGN KEY (user_profile_id) REFERENCES User_profile (id) ON DELETE CASCADE
);
CREATE TABLE Band_wanted_ad
(
    ad_id int NOT NULL,
    PRIMARY KEY (ad_id),
    CONSTRAINT FK_Band_wanted_Ad FOREIGN KEY (ad_id) REFERENCES Ad (id) ON DELETE CASCADE
);
CREATE TABLE Musician_wanted_ad
(
    ad_id            int NOT NULL,
    preferred_gender varchar(1),
    min_age          smallint,
    max_age          smallint,
    PRIMARY KEY (ad_id),
    CONSTRAINT FK_Musician_wanted_Ad FOREIGN KEY (ad_id) REFERENCES Ad (id) ON DELETE CASCADE
);
CREATE TABLE Jam_session_ad
(
    ad_id int NOT NULL,
    PRIMARY KEY (ad_id),
    CONSTRAINT FK_Jam_session_Ad FOREIGN KEY (ad_id) REFERENCES Ad (id) ON DELETE CASCADE
);
CREATE TABLE Ad_voivodeship
(
    ad_id          int NOT NULL,
    voivodeship_id int NOT NULL,
    PRIMARY KEY (ad_id, voivodeship_id),
    CONSTRAINT FK_Ad_Voivodeship_Ad FOREIGN KEY (ad_id) REFERENCES Ad (id) ON DELETE CASCADE,
    CONSTRAINT FK_Ad_Voivodeship_Voivodeship FOREIGN KEY (voivodeship_id) REFERENCES Voivodeship (id)
);
CREATE TABLE Ad_preferred_genre
(
    ad_id    int NOT NULL,
    genre_id int NOT NULL,
    PRIMARY KEY (ad_id, genre_id),
    CONSTRAINT FK_Ad_preferred_genre_Ad FOREIGN KEY (ad_id) REFERENCES Ad (id) ON DELETE CASCADE,
    CONSTRAINT FK_Ad_preferred_genre_Genre FOREIGN KEY (genre_id) REFERENCES Genre (id)
);
CREATE TABLE Ad_preferred_instrument
(
    ad_id         int NOT NULL,
    instrument_id int NOT NULL,
    PRIMARY KEY (ad_id, instrument_id),
    CONSTRAINT FK_Ad_preferred_instrument_Ad FOREIGN KEY (ad_id) REFERENCES Ad (id) ON DELETE CASCADE,
    CONSTRAINT FK_Ad_preferred_instrument_Instrument FOREIGN KEY (instrument_id) REFERENCES Instrument (id)
);
CREATE TABLE User_genre
(
    user_profile_id int NOT NULL,
    genre_id        int NOT NULL,
    PRIMARY KEY (user_profile_id, genre_id),
    CONSTRAINT FK_User_genre_User_profile FOREIGN KEY (user_profile_id) REFERENCES User_profile (id) ON DELETE CASCADE,
    CONSTRAINT FK_User_genre_Genre FOREIGN KEY (genre_id) REFERENCES Genre (id)
);
CREATE TABLE User_instrument
(
    user_profile_id int NOT NULL,
    instrument_id   int NOT NULL,
    PRIMARY KEY (user_profile_id, instrument_id),
    CONSTRAINT FK_User_instrument_User_profile FOREIGN KEY (user_profile_id) REFERENCES User_profile (id) ON DELETE CASCADE,
    CONSTRAINT FK_User_instrument_Instrument FOREIGN KEY (instrument_id) REFERENCES Instrument (id)
);
CREATE TABLE Equipment
(
    id               int         NOT NULL AUTO_INCREMENT,
    name             varchar(60) NOT NULL,
    musician_user_id int         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_Equipment_Musician FOREIGN KEY (musician_user_id) REFERENCES Musician (user_profile_id) ON DELETE CASCADE
);
CREATE TABLE User_image
(
    id              int           NOT NULL AUTO_INCREMENT,
    filename        varchar(255)  NOT NULL,
    link            varchar(1000) NOT NULL,
    user_profile_id int           NOT NULL,
    order_index     int           NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_Image_User_profile FOREIGN KEY (user_profile_id) REFERENCES User_profile (id) ON DELETE CASCADE
);
CREATE TABLE Social_media_links
(
    user_profile_id int NOT NULL,
    youtube         varchar(1000),
    soundcloud      varchar(1000),
    webpage         varchar(1000),
    version         int NOT NULL,
    PRIMARY KEY (user_profile_id),
    CONSTRAINT FK_Social_media_links_User_profile FOREIGN KEY (user_profile_id) REFERENCES User_profile (id) ON DELETE CASCADE
);
CREATE TABLE Chat_message
(
    id                        bigint        NOT NULL AUTO_INCREMENT,
    sender_user_profile_id    int           NOT NULL,
    recipient_user_profile_id int           NOT NULL,
    content                   varchar(2000) NOT NULL,
    sent_at                   timestamp     NOT NULL DEFAULT (utc_timestamp()),
    seen                      boolean       NOT NULL DEFAULT (false),
    PRIMARY KEY (id),
    CONSTRAINT FK_Chat_message_sender_User_profile FOREIGN KEY (sender_user_profile_id) REFERENCES User_profile (id) ON DELETE CASCADE,
    CONSTRAINT FK_Chat_message_recipient_User_profile FOREIGN KEY (recipient_user_profile_id) REFERENCES User_profile (id) ON DELETE CASCADE
)
