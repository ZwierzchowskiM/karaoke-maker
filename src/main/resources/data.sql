INSERT INTO
    chords(single_note,type,length,path,complexity)
VALUES
     ('C','MAJ',4,'Files/audioFiles/Source/C/Major/Cmaj.wav',4),
     ('D','MAJ',4,'Files/audioFiles/Source/D/Major/Dmaj.wav',2),
     ('C','MAJ',4,'Files/audioFiles/Source/C/Major/Cmaj.wav',3),
     ('D','MAJ',4,'Files/audioFiles/Source/D/Major/Dmaj.wav',2);


INSERT INTO
    users (first_name, last_name, email, password,role)
VALUES
    -- superadmin@example.com / hard
    ('Jan', 'Kowalski', 'superadmin@example.com', '{bcrypt}$2a$10$Ruu5GtmSVkfLeuGfz/wHUuzflCcMbwJHSBo/.Wui0EM0KIM52Gs2S','ADMIN'),
    -- mzwierzchowski@example.com / admin
    ('Marcin', 'Zwierzchowski', 'mzwierzchowski@example.com', '{bcrypt}$2a$10$nX5RgASROsIyu3RLZO3c6OfMgu4YYyU8dZtVtsZBZKskV8kV2NG.S','USER');

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