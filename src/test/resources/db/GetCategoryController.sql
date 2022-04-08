INSERT INTO T_CATEGORY(id, parent_fk, title, title_french, product_count, published_product_count)
    VALUES
        (100, null, 'Electronic', 'Électronique', 10, 5),
        (101, 100, 'Cell Phone', 'Téléphone portable', 11, 7),
        (102, 100, 'TV', 'TV', 12, 8),
        (103, 100, 'Camera', 'Appareil Photo', 13, 9),

        (110, null, 'Home', 'Maison', 0, 0)
;
