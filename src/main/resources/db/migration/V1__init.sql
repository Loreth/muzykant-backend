#strong entities
CREATE TABLE Credentials
(
    id       int          NOT NULL AUTO_INCREMENT,
    email    varchar(255) NOT NULL UNIQUE,
    username varchar(30)  NOT NULL UNIQUE,
    password char(60)     NOT NULL,
    PRIMARY KEY (id)
);
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
    gender     char(1)     NOT NULL,
    birthdate  date        NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Genre
(
    id   int         NOT NULL AUTO_INCREMENT,
    name varchar(30) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Vocal_range
(
    id           int     NOT NULL AUTO_INCREMENT,
    lowest_note  char(2) NOT NULL,
    highest_note char(2) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Vocal_technique
(
    id   int         NOT NULL AUTO_INCREMENT,
    name varchar(30) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Instrument
(
    id          int         NOT NULL AUTO_INCREMENT,
    name        varchar(40) NOT NULL UNIQUE,
    player_name varchar(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
#weak entities
CREATE TABLE `User`
(
    id             int         NOT NULL AUTO_INCREMENT,
    link_name      varchar(30) NOT NULL UNIQUE,
    description    varchar(400),
    phone          varchar(60),
    city           varchar(60),
    voivodeship_id int         NOT NULL,
    credentials_id int         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_User_Voivodeship FOREIGN KEY (voivodeship_id) REFERENCES Voivodeship (id),
    CONSTRAINT FK_User_Credentials FOREIGN KEY (credentials_id) REFERENCES Credentials (id)
);
CREATE TABLE Ad
(
    id               int     NOT NULL AUTO_INCREMENT,
    published_date   date,
    description      varchar(400),
    preferred_gender char(1),
    commercial       tinyint NOT NULL,
    user_id          int     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_Ad_User FOREIGN KEY (user_id) REFERENCES `User` (id)
);
CREATE TABLE Band
(
    user_id        int          NOT NULL,
    name           varchar(100) NOT NULL,
    formation_year smallint,
    PRIMARY KEY (user_id),
    CONSTRAINT FK_Band_User FOREIGN KEY (user_id) REFERENCES `User` (id)
);
CREATE TABLE Musician
(
    user_id        int NOT NULL,
    person_id      int NOT NULL,
    vocal_range_id int,
    PRIMARY KEY (user_id),
    CONSTRAINT FK_Musician_User FOREIGN KEY (user_id) REFERENCES `User` (id),
    CONSTRAINT FK_Musician_Person FOREIGN KEY (person_id) REFERENCES Person (id),
    CONSTRAINT FK_Musician_Vocal_range FOREIGN KEY (vocal_range_id) REFERENCES Vocal_range (id)
);
CREATE TABLE Regular_user
(
    user_id   int NOT NULL,
    person_id int NOT NULL,
    PRIMARY KEY (user_id),
    CONSTRAINT FK_Regular_user_User FOREIGN KEY (user_id) REFERENCES `User` (id),
    CONSTRAINT FK_Regular_user_Person FOREIGN KEY (person_id) REFERENCES Person (id)
);
CREATE TABLE Band_wanted_ad
(
    ad_id int NOT NULL,
    PRIMARY KEY (ad_id),
    CONSTRAINT FK_Band_wanted_Ad FOREIGN KEY (ad_id) REFERENCES Ad (id)
);
CREATE TABLE Musician_wanted_ad
(
    ad_id          int NOT NULL,
    min_age        tinyint,
    max_age        tinyint,
    vocal_range_id int,
    PRIMARY KEY (ad_id),
    CONSTRAINT FK_Musician_wanted_Ad FOREIGN KEY (ad_id) REFERENCES Ad (id),
    CONSTRAINT FK_Musician_wanted_Vocal_range FOREIGN KEY (vocal_range_id) REFERENCES Vocal_range (id)
);
CREATE TABLE Jam_session_ad
(
    ad_id    int NOT NULL,
    location varchar(255),
    PRIMARY KEY (ad_id),
    CONSTRAINT FK_Jam_session_Ad FOREIGN KEY (ad_id) REFERENCES Ad (id)
);
CREATE TABLE Ad_preferred_genre
(
    ad_id    int NOT NULL,
    genre_id int NOT NULL,
    PRIMARY KEY (ad_id, genre_id),
    CONSTRAINT FK_Ad_preferred_genre_Ad FOREIGN KEY (ad_id) REFERENCES Ad (id),
    CONSTRAINT FK_Ad_preferred_genre_Genre FOREIGN KEY (genre_id) REFERENCES Genre (id)
);
CREATE TABLE Ad_preferred_instrument
(
    ad_id         int NOT NULL,
    instrument_id int NOT NULL,
    PRIMARY KEY (ad_id, instrument_id),
    CONSTRAINT FK_Ad_preferred_instrument_Instrument FOREIGN KEY (instrument_id) REFERENCES Instrument (id),
    CONSTRAINT FK_Ad_preferred_instrument_Ad FOREIGN KEY (ad_id) REFERENCES Ad (id)
);
CREATE TABLE Ad_preferred_vocal_technique
(
    ad_id              int NOT NULL,
    vocal_technique_id int NOT NULL,
    PRIMARY KEY (ad_id, vocal_technique_id),
    CONSTRAINT FK_Ad_preferred_vocal_technique_Ad FOREIGN KEY (ad_id) REFERENCES Ad (id),
    CONSTRAINT FK_Ad_preferred_vocal_technique_Vocal_technique FOREIGN KEY (vocal_technique_id) REFERENCES Vocal_technique (id)
);
CREATE TABLE Predefined_vocal_range
(
    vocal_range_id int         NOT NULL,
    name           varchar(20) NOT NULL UNIQUE,
    PRIMARY KEY (vocal_range_id),
    CONSTRAINT FK_Predefined_vocal_range_Vocal_range FOREIGN KEY (vocal_range_id) REFERENCES Vocal_range (id)
);
CREATE TABLE User_genre
(
    user_id  int NOT NULL,
    genre_id int NOT NULL,
    PRIMARY KEY (user_id, genre_id),
    CONSTRAINT FK_User_genre_User FOREIGN KEY (user_id) REFERENCES `User` (id),
    CONSTRAINT FK_User_genre_Genre FOREIGN KEY (genre_id) REFERENCES Genre (id)
);
CREATE TABLE User_instrument
(
    user_id       int NOT NULL,
    instrument_id int NOT NULL,
    PRIMARY KEY (user_id, instrument_id),
    CONSTRAINT FK_User_instrument_User FOREIGN KEY (user_id) REFERENCES `User` (id),
    CONSTRAINT FK_User_instrument_Instrument FOREIGN KEY (instrument_id) REFERENCES Instrument (id)
);
CREATE TABLE User_vocal_technique
(
    user_id            int NOT NULL,
    vocal_technique_id int NOT NULL,
    PRIMARY KEY (user_id, vocal_technique_id),
    CONSTRAINT FK_User_vocal_technique_User FOREIGN KEY (user_id) REFERENCES `User` (id),
    CONSTRAINT FK_User_vocal_technique_Vocal_technique FOREIGN KEY (vocal_technique_id) REFERENCES Vocal_technique (id)
);
CREATE TABLE Equipment
(
    id               int         NOT NULL AUTO_INCREMENT,
    name             varchar(60) NOT NULL,
    musician_user_id int         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_Equipment_Musician FOREIGN KEY (musician_user_id) REFERENCES Musician (user_id)
);
CREATE TABLE Image
(
    id      int           NOT NULL AUTO_INCREMENT,
    link    varchar(1000) NOT NULL,
    user_id int           NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_Image_User FOREIGN KEY (user_id) REFERENCES `User` (id)
);
