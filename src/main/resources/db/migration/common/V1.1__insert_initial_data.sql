INSERT INTO Voivodeship(id, name)
VALUES (1, 'dolnośląskie'),
       (2, 'kujawsko-pomorskie'),
       (3, 'lubelskie'),
       (4, 'lubuskie'),
       (5, 'łódzkie'),
       (6, 'małopolskie'),
       (7, 'mazowieckie'),
       (8, 'opolskie'),
       (9, 'podkarpackie'),
       (10, 'podlaskie'),
       (11, 'pomorskie'),
       (12, 'śląskie'),
       (13, 'świętokrzyskie'),
       (14, 'warmińsko-mazurskie'),
       (15, 'wielkopolskie'),
       (16, 'zachodniopomorskie');

INSERT INTO Person(id, first_name, last_name, pseudo, gender, birthdate)
VALUES (1, 'Adam', 'Dąbek', 'Adi', 'M', '1997-02-03'),
       (2, 'Daniel', 'Wójcikowski', 'Dan', 'M', '1994-04-07'),
       (3, 'Andrzej', 'Zalewski', 'Grajek', 'M', '1985-11-12'),
       (4, 'Joanna', 'Wieczorowska', null, 'F', '1990-08-15'),
       (5, 'Wiktoria', 'Dobrzyńska', 'Wiki', 'F', '2000-01-02'),
       (6, 'Dorota', 'Kozera', null, 'F', '1950-02-16'),
       (7, 'Adam', 'Kowalski', 'Adamkowski', 'M', '1941-06-23'),
       (8, 'Damian', 'Niedzielski', 'Klub Nietota', 'M', '1980-12-05');

INSERT INTO Genre(id, name)
VALUES (1, 'Pop'),
       (2, 'Rap'),
       (3, 'Rock'),
       (4, 'Latino'),
       (5, 'Hip hop'),
       (6, 'Trap'),
       (7, 'EDM'),
       (8, 'Rock progresywny'),
       (9, 'Reggae'),
       (10, 'Grunge'),
       (11, 'Metal'),
       (12, 'Metal progresywny'),
       (13, 'Alternatywa'),
       (14, 'Indie'),
       (15, 'Blues'),
       (16, 'Celtic'),
       (17, 'Muzyka klasyczna'),
       (18, 'Country'),
       (19, 'Disco Polo'),
       (20, 'Djent'),
       (21, 'Dubstep'),
       (22, 'Elektronika'),
       (23, 'House'),
       (24, 'Hard Rock'),
       (25, 'Industrial'),
       (26, 'New Age'),
       (27, 'New Wave'),
       (28, 'Opera'),
       (29, 'Piosenka poetycka'),
       (30, 'Punk'),
       (31, 'Ska'),
       (32, 'Techno'),
       (33, 'Trash Metal'),
       (34, 'Death Metal'),
       (35, 'Muzyka weselna'),
       (36, 'Punk Rock'),
       (37, 'Math Rock'),
       (38, 'Jazz');

INSERT INTO Vocal_range(id, lowest_note, highest_note)
VALUES (1, 'C4', 'C6'),
       (2, 'A3', 'A5'),
       (3, 'F3', 'E5'),
       (4, 'H2', 'A4'),
       (5, 'G2', 'F4'),
       (6, 'E2', 'E4');

INSERT INTO Vocal_technique(id, name)
VALUES (1, 'Growl'),
       (2, 'Vibrato');

INSERT INTO Instrument(id, name)
VALUES (1, 'Akordeon'),
       (2, 'Altówka'),
       (3, 'Banjo'),
       (4, 'Bas'),
       (5, 'Bongosy'),
       (6, 'Cymbały'),
       (7, 'DAW'),
       (8, 'DJ'),
       (9, 'Dudy'),
       (10, 'Flet'),
       (11, 'Fortepian'),
       (12, 'Harfa'),
       (13, 'Harmonijka'),
       (14, 'Kitara'),
       (15, 'Klarnet'),
       (16, 'Klawesyn'),
       (17, 'Klawisze'),
       (18, 'Kontrabas'),
       (19, 'Kornet'),
       (20, 'Koto'),
       (21, 'Ksylofon'),
       (22, 'Lira'),
       (23, 'Lutnia'),
       (24, 'Mandolina'),
       (25, 'Marakasy'),
       (26, 'Marimba'),
       (27, 'Obój'),
       (28, 'Perkusja'),
       (29, 'Pianino'),
       (30, 'Puzon'),
       (31, 'Saksofon'),
       (32, 'Sitar'),
       (33, 'Skrzypce'),
       (34, 'Syntezator'),
       (35, 'Tamburyn'),
       (36, 'Trąbka'),
       (37, 'Trójkąt'),
       (38, 'Tuba'),
       (39, 'Ukulele'),
       (40, 'Waltornia'),
       (41, 'Wibrafon'),
       (42, 'Wiolonczela'),
       (43, 'Wokal główny'),
       (44, 'Wokal wspierający'),
       (45, 'Gitara elektryczna'),
       (46, 'Gitara akustyczna'),
       (47, 'Gitara klasyczna'),
       (48, 'Flet poprzeczny');

INSERT INTO Authority(id, name)
VALUES (1, 'ROLE_MUSICIAN'),
       (2, 'ROLE_BAND'),
       (3, 'ROLE_REGULAR_USER');

INSERT INTO User_profile(id, user_type, link_name, description, phone, city, voivodeship_id)
VALUES (1, 'REGULAR', 'adi', 'Szukam zespołu na wesele, proszę o kontakt!', '837473123', 'Wrocław',
        1),
       (2, 'MUSICIAN', 'dani', null, '125373123', 'Wrocław', 1),
       (3, 'MUSICIAN', 'grajek',
        'Gram dużo i wszystko.
Ale przede wszystkim cenię dobry rock i metal.
Mam własne studio i dużo sprzętu, których mogę użyczyć.
Z doświadczenia wiem, że gra z nieznajomymi to zawsze ciekawe doświadczenie, więc serdecznie zapraszam do kontaktu na portalu lub przez telefon i wspólnej gry :)',
        '923645183',
        'Radwanice', 1),
       (4, 'MUSICIAN', 'slash', 'Take me down to the paradise city
Where the grass is green and the girls are pretty 😎🤘', '182543765', 'Katowice', 12),
       (5, 'MUSICIAN', 'koko', null, null, 'Sosnowiec', 12),
       (6, 'BAND', 'swietni', null, '458342643', 'Wrocław', 1),
       (7, 'BAND', 'muzykanci', null, '123123123', 'Wrocław', 1),
       (8, 'BAND', 'zagubieni', 'Zagubieni w czasie. Zagubieni w muzyce.', null, 'Warszawa', 7),
       (9, 'MUSICIAN', 'cool', null, null, 'Wrocław', 1),
       (10, 'MUSICIAN', 'kingkong', null, null, 'Sosnowiec', 12),
       (11, 'REGULAR', 'klub_nietota', 'Klub Nietota', '876421423', 'Wrocław', 1);

UPDATE User_profile
SET profile_image_link='http://localhost:8080/user-images/image-uploads/3_profile-image.jpg'
WHERE id = 3;
UPDATE User_profile
SET profile_image_link='http://localhost:8080/user-images/image-uploads/8_profile-image.jpg'
WHERE id = 8;

INSERT INTO Credentials(id, email, password, authority_id, user_profile_id)
VALUES (1, 'adam@gmail.com', '$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC', 3,
        1),                                                                          -- mocnehaslo123
       (2, 'daniel@gmail.com', '$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC', 1,
        2),
       (3, 'grajek@gmail.com', '$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC', 1,
        3),
       (4, 'muzyk@yahoo.com', '$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC', 1, 4),
       (5, 'ciekawska@yahoo.com', '$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC', 1,
        5),
       (6, 'superzespol@yahoo.com', '$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC',
        2, 6),
       (7, 'anna.kowalska@onet.pl', '$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC',
        2, 7),
       (8, 'zagubieni@onet.pl', '$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC',
        2, 8),
       (9, 'email@gmail.com', '$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC', null,
        null),                                                                       -- mocnehaslo123
       (10, 'unconfirmedmail@unconfirmed.com',
        '$2a$10$54JRdhDbH4Y9uqgDvKVeb.6sCpdDhATx2UjJ8BLowy2RkDEkvU/1O', null, null), -- passpass56
       (11, 'expired_token@expired.pl',
        '$2a$10$OMOjLn0RC73SO6wc3EV8L.GmuLNGoObipuEwFu6r8iW5uU4WKWmqq', null, null), -- passpass56
       (12, 'confirmed@nouser.com',
        '$2a$10$qmz6rxBdiAtcuLLK.lPAcubIiSCYexVjZfEBH.66/.A982dCfLiya', null, null), -- passpass56
       (13, 'ktos@gmail.com', '$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC',
        1, 9),-- mocnehaslo123
       (14, 'szybki@gazeta.pl', '$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC',
        1, 10),                                                                      -- mocnehaslo123
       (15, 'klub_nietota@gmail.com',
        '$2a$10$zifVLlf8WvlHvfeLLiqvKe44GxtATH2uo2TPjl6VSuRmHC/9rkFJC',
        3, 11); -- mocnehaslo123

INSERT INTO Email_confirmation(credentials_id, token_uuid, token_expiration)
VALUES (9, 'c614e388-6bc3-4abc-87cb-e4d8dbd73b3c', '9999-12-31 23:59:59'),
       (10, '6d8e6ea9-8af9-4716-96bc-6f910ef61b76', '2100-12-31 23:59:59'),
       (11, 'a163ca90-5e57-4861-9e21-119d26923c63', '2020-08-08 12:00:12');

INSERT INTO Band(user_profile_id, name, formation_year)
VALUES (6, 'Turbo akcja', 2016),
       (7, 'Szakalaka', 2020),
       (8, 'Zagubieni w czasie', 1999);

INSERT INTO Musician(user_profile_id, person_id, vocal_range_id)
VALUES (2, 2, null),
       (3, 3, null),
       (4, 4, null),
       (5, 5, null),
       (9, 6, null),
       (10, 7, null);

INSERT INTO Regular_user(user_profile_id, person_id)
VALUES (1, 1),
       (11, 8);

INSERT INTO Ad(id, ad_type, published_date, location, description, commercial, user_profile_id)
VALUES (1, 'BAND_WANTED', '2020-07-19', 'Wrocław', null, false, 1),
       (2, 'BAND_WANTED', '2020-08-10', 'Radwanice', null, true, 1),
       (3, 'MUSICIAN_WANTED', '2020-07-04', 'Wrocław', 'Zapraszam do wspólnej gry :)', false, 2),
       (4, 'MUSICIAN_WANTED', '2020-08-15', 'Warszawa i okolice',
        'Szukam perkusisty do małej serii klubowych koncertów w duecie.', true, 3),
       (5, 'MUSICIAN_WANTED', '2020-07-14', 'Zamość',
        'Życiowa szansa na zaistnienie u boku prawdziwej gwiazdy. Tylko zawodowcy! Szczegóły po wstępnej rozmowie.',
        true, 4),
       (6, 'MUSICIAN_WANTED', '2020-08-02', 'Katowice', null, false, 5),
       (7, 'MUSICIAN_WANTED', '2020-08-23', 'Wrocław',
        'Poszukiwany wokalista do nagrania okazjonalnych wstawek w naszych piosenkach', false, 6),
       (8, 'JAM_SESSION', '2020-06-08', 'Radwanice',
        'Ostrzegam, nie jestem najlepszy (głównie pentatonika), ale szybko się uczę :). Ktoś chętny na wspólny jam?',
        false, 5),
       (9, 'JAM_SESSION', '2020-07-23', 'Radwanice/Wrocław',
        'Mam studio (perkusja, bas, gitary, dużo ampów i wiele więcej - chętnie użyczę), można razem pobrzdąkać do woli',
        false, 3),
       (10, 'JAM_SESSION', '2020-07-24', 'Wrocław, klub Nietota',
        'Zapraszamy na jam session w naszym klubie każdego piątkowego wieczoru. Zainteresowanych prosimy o kontakt telefoniczny.',
        false, 1),
       (11, 'MUSICIAN_WANTED', '2020-08-12', 'Warszawa',
        'Poszukujemy nowego członka zespołu. Jeśli chcesz się stać częścią czegoś prawdziwie wyjątkowego, to dobrze trafiłeś. Tworzymy muzykę z duszą, dając od siebie wszystko. Podróżujemy przez czas mieszając gatunki ze wszystkich dziesięcioleci. Nie interesuje nas Twój wiek ani staż muzyczny - ważna jest przede wszystkim kreatywność.',
        true, 8);

INSERT INTO Band_wanted_ad(ad_id)
VALUES (1),
       (2);

INSERT INTO Musician_wanted_ad(ad_id, preferred_gender, min_age, max_age, vocal_range_id)
VALUES (3, null, null, null, null),
       (4, 'M', 20, 50, null),
       (5, null, 35, 58, null),
       (6, 'F', 30, 60, null),
       (7, 'M', null, null, null),
       (11, null, null, null, null);

INSERT INTO Jam_session_ad(ad_id)
VALUES (8),
       (9),
       (10);

INSERT INTO Ad_voivodeship(ad_id, voivodeship_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 7),
       (5, 3),
       (6, 12),
       (7, 1),
       (8, 12),
       (9, 1);

INSERT INTO Ad_preferred_genre(ad_id, genre_id)
VALUES (1, 12),
       (1, 23),
       (2, 5),
       (2, 8),
       (4, 24),
       (4, 3),
       (5, 3),
       (5, 36),
       (5, 24),
       (5, 10),
       (8, 35),
       (8, 4),
       (8, 3),
       (9, 35),
       (11, 13),
       (11, 14);

INSERT INTO Ad_preferred_instrument(ad_id, instrument_id)
VALUES (1, 5),
       (1, 34),
       (1, 15),
       (4, 28),
       (5, 45),
       (5, 46),
       (6, 1),
       (6, 2),
       (6, 33),
       (7, 43),
       (7, 44),
       (8, 7),
       (11, 31),
       (11, 41),
       (11, 17),
       (11, 21);

INSERT INTO Predefined_vocal_range(vocal_range_id, name)
VALUES (1, 'sopran'),
       (2, 'mezzosopran'),
       (3, 'kontralt'),
       (4, 'tenor'),
       (5, 'baryton'),
       (6, 'bas');

INSERT INTO User_genre(user_profile_id, genre_id)
VALUES (2, 1),
       (2, 4),
       (3, 3),
       (3, 8),
       (3, 11),
       (3, 24),
       (3, 30),
       (3, 33),
       (3, 36),
       (4, 3),
       (4, 10),
       (4, 11),
       (4, 24),
       (4, 30),
       (5, 3),
       (5, 13),
       (5, 15),
       (6, 7),
       (6, 21),
       (6, 22),
       (6, 23),
       (7, 26),
       (7, 27),
       (8, 13),
       (8, 22),
       (8, 14),
       (8, 25),
       (8, 26),
       (8, 27);

INSERT INTO User_instrument(user_profile_id, instrument_id)
VALUES (2, 7),
       (2, 8),
       (2, 34),
       (3, 4),
       (3, 17),
       (3, 28),
       (3, 29),
       (3, 34),
       (3, 44),
       (3, 45),
       (3, 46),
       (4, 13),
       (4, 46),
       (4, 47),
       (5, 29),
       (5, 34),
       (5, 44),
       (5, 46),
       (7, 5),
       (7, 15),
       (8, 11),
       (8, 24),
       (8, 25),
       (8, 26),
       (8, 36);

-- TODO
--  INSERT INTO User_vocal_technique(user_profile_id, vocal_technique_id)
--  VALUES ();

INSERT INTO Equipment(id, name, musician_user_id)
VALUES (1, 'Marshall DSL40', 2),
       (2, 'Vox AC30', 2),
       (3, 'Shure SM47 x2', 2),
       (4, 'Yamaha PSR-EW410 (keyboard)', 4);

INSERT INTO Social_media_links(user_profile_id, youtube, soundcloud, webpage)
VALUES (3, null, 'https://soundcloud.com/muse', null);
