INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable'),
        (102, 100, 'TV', 'TV'),

        (200, null, 'Home', 'Maison'),
        (201, 200, 'Living Room', 'Salon'),
        (202, 200, 'Bathroom', 'Salle de Bain')
;

INSERT INTO T_PRODUCT(id, tenant_id, account_id, category_fk, sub_category_fk, title, summary, description, price, comparable_price, currency, visible, is_deleted)
    VALUES
        (100, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),
        (101, 1, 1, 100, 102, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),
        (109, 1, 1, 100, 102, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true),

        (200, 1, 2, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),
        (201, 1, 2, 200, 202, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),

        (300, 1, 3, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, false),

        (900, 1, 9, 200, 102, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true),

        (2100, 2, 10, 200, 202, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true),
        (2200, 2, 10, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true)
;

INSERT INTO T_PICTURE(id, product_fk, url, is_deleted, deleted)
    VALUES
        (100, 100, 'https://img.com/1.png', false, null),
        (101, 100, 'https://img.com/2.png', false, null),
        (102, 100, 'https://img.com/3.png', true, now());

UPDATE T_PRODUCT SET thumbnail_fk=100;
