INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable'),
        (102, 100, 'TV', 'TV'),
        (103, 100, 'Camera', 'Appareil Photo'),

        (110, null, 'Home', 'Maison')
;
