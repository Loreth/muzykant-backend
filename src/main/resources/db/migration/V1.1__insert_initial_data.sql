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
       (4, 'Joanna', 'Wieczorowska', null, 'K', '1990-08-15'),
       (5, 'Wiktoria', 'Dobrzyńska', 'Wiki', 'K', '2000-01-02');


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
       (27, 'New wave'),
       (28, 'Opera'),
       (29, 'Piosenka poetycka'),
       (30, 'Punk'),
       (31, 'Ska'),
       (32, 'Techno'),
       (33, 'Trash Metal'),
       (34, 'Death Metal'),
       (35, 'Muzyka weselna');

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

INSERT INTO `User`(id, link_name, description, phone, city, voivodeship_id)
VALUES (1, 'adi', 'Coś o mnie', '837473123', 'Wrocław', 1),
       (2, 'dani', 'Coś o mnie', '125373123', 'Wrocław', 1),
       (3, 'grajek', 'Coś o mnie', '923645183', 'Radwanice', 1),
       (4, 'slash', 'Coś o mnie', '182543765', 'Katowice', 12),
       (5, 'koko', 'Coś o mnie', null, 'Sosnowiec', 12),
       (6, 'swietni', 'Coś o nas', '458342643', 'Wrocław', 1),
       (7, 'muzykanci', 'Coś o nas', '123123123', 'Wrocław', 1),
       (8, 'jk', 'Coś o nas', null, 'Warszawa', 7);

INSERT INTO Credentials(id, email, password, user_id, user_type)
VALUES (1, 'adam@gmail.com', 'TEMPORARY', 1, 'REGULAR'),
       (2, 'daniel@gmail.com', 'TEMPORARY', 2, 'MUSICIAN'),
       (3, 'grajek@gmail.com', 'TEMPORARY', 3, 'MUSICIAN'),
       (4, 'muzyk@yahoo.com', 'TEMPORARY', 4, 'MUSICIAN'),
       (5, 'ciekawska@yahoo.com', 'TEMPORARY', 5, 'MUSICIAN'),
       (6, 'superzespol@yahoo.com', 'TEMPORARY', 6, 'BAND'),
       (7, 'anna.kowalska@onet.pl', 'TEMPORARY', 7, 'BAND'),
       (8, 'jan.kowalski@onet.pl', 'TEMPORARY', 8, 'BAND');

INSERT INTO Band(user_id, name, formation_year)
VALUES (6, 'Turbo akcja', 2016),
       (7, 'Szakalaka', 2020),
       (8, 'Revolta', 2010);

INSERT INTO Musician(user_id, person_id, vocal_range_id)
VALUES (2, 2, null),
       (3, 3, null),
       (4, 4, null),
       (5, 5, null);

INSERT INTO Regular_user(user_id, person_id)
VALUES (1, 1);

INSERT INTO Ad(id, published_date, location, description, commercial, user_id)
VALUES (1, '2020-07-19', 'Wrocław', null, false, 1),
       (2, '2020-08-10', 'Radwanice', null, true, 1),
       (3, '2020-08-12', 'Wrocław', 'Zapraszam do wspólnej gry :)', false, 2),
       (4, '2020-08-13', 'Warszawa', 'Opis opis', false, 3),
       (5, '2020-07-14', 'Zamość', 'Tylko zawodowcy!', true, 4),
       (6, '2020-08-13', 'Katowice', null, false, 5),
       (7, '2020-08-23', 'Wrocław', null, false, 6),
       (8, '2020-06-10', 'Radwanice',
        'Ostrzegam, nie jestem najlepszy(głównie pentatonika), ale szybko się uczę :D. Ktoś chętny na wspólny jam?',
        false, 3),
       (9, '2020-07-23', 'Katowice',
        'Mam studio (perksuja, bas, gitary i dużo ampów - chętnie użyczę), można razem pobrzdąkać do woli',
        false, 8),
       (10, '2020-07-23', 'Wrocław, klub Nietota',
        'Zapraszamy na jam session w naszym klubie każdego piątkowego wieczoru. Zainteresowanych prosimy o kontakt telefoniczny.',
        false, 1);

INSERT INTO Band_wanted_ad(ad_id)
VALUES (1),
       (2);

INSERT INTO Musician_wanted_ad(ad_id, preferred_gender, min_age, max_age, vocal_range_id)
VALUES (3, null, null, null, null),
       (4, 'K', 20, 30, null),
       (5, 'K', 45, 60, null),
       (6, 'M', 30, 60, null),
       (7, 'M', null, null, null);

INSERT INTO Jam_session_ad(ad_id)
VALUES (8),
       (9),
       (10);

INSERT INTO ad_preferred_genre(ad_id, genre_id)
VALUES (1, 12),
       (1, 23),
       (2, 5),
       (2, 8),
       (4, 3),
       (4, 4),
       (5, 1),
       (5, 7),
       (5, 12),
       (5, 22),
       (5, 31),
       (8, 35),
       (8, 4),
       (8, 3),
       (9, 35);

INSERT INTO ad_preferred_instrument(ad_id, instrument_id)
VALUES (1, 5),
       (1, 34),
       (1, 15),
       (4, 1),
       (4, 2),
       (4, 3),
       (5, 1),
       (5, 4),
       (5, 12),
       (6, 43),
       (6, 1),
       (6, 2),
       (6, 3),
       (7, 47),
       (7, 46),
       (8, 7);

INSERT INTO predefined_vocal_range(vocal_range_id, name)
VALUES (1, 'sopran'),
       (2, 'mezzosopran'),
       (3, 'kontralt'),
       (4, 'tenor'),
       (5, 'baryton'),
       (6, 'bas');

INSERT INTO User_genre(user_id, genre_id)
VALUES (2, 1),
       (2, 4),
       (3, 3),
       (3, 11),
       (3, 8),
       (4, 3),
       (5, 15),
       (5, 3),
       (5, 13),
       (6, 7),
       (6, 22),
       (6, 21),
       (6, 23),
       (7, 26),
       (7, 27),
       (8, 3),
       (8, 10),
       (8, 24),
       (8, 11),
       (8, 30);

INSERT INTO User_instrument(user_id, instrument_id)
VALUES (2, 8),
       (2, 7),
       (2, 34),
       (3, 45),
       (3, 46),
       (3, 43),
       (4, 46),
       (4, 47),
       (4, 13),
       (5, 29),
       (5, 44),
       (5, 46),
       (5, 34),
       (7, 5),
       (7, 15),
       (8, 24),
       (8, 25),
       (8, 26),
       (8, 11),
       (8, 36);

-- TODO
--  INSERT INTO User_vocal_technique(user_id, vocal_technique_id)
--  VALUES ();

INSERT INTO Equipment(id, name, musician_user_id)
VALUES (1, 'Marshall DSL40', 2),
       (2, 'Vox AC30', 2),
       (3, 'Shure SM47 x2', 2),
       (4, 'Yamaha PSR-EW410 (keyboard)', 4);


INSERT INTO Image(id, link, user_id)
VALUES (1, 'placeholder', 2),
       (2, 'placeholder', 2),
       (3, 'placeholder', 3);

