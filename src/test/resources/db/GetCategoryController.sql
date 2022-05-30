INSERT INTO T_CATEGORY(id, parent_fk, title, title_french, google_product_category_id)
    VALUES
        (100, null, 'Electronic', 'Électronique', 1001),
        (101, 100, 'Cell Phone', 'Téléphone portable', 1011),
        (102, 100, 'TV', 'TV', 1021),
        (103, 100, 'Camera', 'Appareil Photo', 1031),

        (110, null, 'Home', 'Maison',1101)
;
