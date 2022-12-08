INSERT INTO
    chords(single_note,type,lenght,path,part_name)
VALUES
     ('C','MAJ',4,'Files/audioFiles/Source/C/Major/Cmaj.wav','ZW'),
     ('D','MAJ',4,'Files/audioFiles/Source/D/Major/Dmaj.wav','ZW'),
     ('C','MAJ',4,'Files/audioFiles/Source/C/Major/Cmaj.wav','REF'),
     ('D','MAJ',4,'Files/audioFiles/Source/D/Major/Dmaj.wav','REF'),
     ('E','MAJ',4,'/resoureces/audioFiles/Source/C/Major/Cmaj.wav','ZW'),
     ('G','MAJ9',4,'/resoureces/audioFiles/Source/C/Major/Cmaj.wav','REF'),
     ('F','MAJ',4,'/resoureces/audioFiles/Source/C/Major/Cmaj.wav','REF');


INSERT INTO
    users (first_name, last_name, email, password, role)
VALUES
    -- superadmin@example.com / hard
    ('Jan', 'Kowalski', 'superadmin@example.com', '{bcrypt}$2a$10$Ruu5GtmSVkfLeuGfz/wHUuzflCcMbwJHSBo/.Wui0EM0KIM52Gs2S', 'ADMIN'),
    -- admin@example.com / admin
    ('Marcin', 'Zwierzchowski', 'mzwierzchowski@example.com', '{bcrypt}$2a$10$0YszjxV5RyKYNg653b1EbOrC2rTQftTqH/oVKZCq.gK3Q83kDmlZy', 'ADMIN'),
    -- john@example.com / dog.8
    ('John', 'Abacki', 'john@example.com', '{MD5}{AlZCLSQMMNLBS5mEO0kSem9V3mxplC6cTjWy9Kj/Gxs=}d9007147eb3a5f727b2665d647d36e35','USER'),
    -- java_lover@example.com / javaiscool
    ('Marek', 'Zalewski', 'java_lover@example.com', '{argon2}$argon2id$v=19$m=4096,t=3,p=1$YBBBwx+kfrNgczYDcLlWYA$LEPgdtfskoobyFtUWTMejaE5SBRyieHYbiE5ZmFKE7I','USER');

--INSERT INTO
--    user_role (name, description)
--VALUES
--    ('ADMIN', 'Ma dostęp do wszystkiego'),
--    ('USER', 'Dostęp tylko do odczytu');
--
--INSERT INTO
--    user_roles (user_id, role_id)
--VALUES
--    (1, 1),
--    (2, 2),
--    (3, 2);