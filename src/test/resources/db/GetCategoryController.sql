INSERT INTO T_CATEGORY(id, parent_fk, title, title_french, product_count, published_product_count, google_product_category_id)
    VALUES
        (100, null, 'Electronic', 'Électronique', 10, 5, 1001),
        (101, 100, 'Cell Phone', 'Téléphone portable', 11, 7, 1011),
        (102, 100, 'TV', 'TV', 12, 8, 1021),
        (103, 100, 'Camera', 'Appareil Photo', 13, 9, 1031),

        (110, null, 'Home', 'Maison', 0, 0, 1101)
;
