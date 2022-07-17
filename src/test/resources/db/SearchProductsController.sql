INSERT INTO T_MERCHANT(tenant_id, account_id, is_enabled, city_id)
    VALUES
        (1, 1, true, 111),
        (1, 2, true, 111),
        (1, 3, true, 333),
        (1, 5, false, null),
        (1, 9, true, null),
        (1, 10, true, null)
;

INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable'),
        (102, 100, 'TV', 'TV'),

        (200, null, 'Home', 'Maison'),
        (201, 200, 'Living Room', 'Salon'),
        (202, 200, 'Bathroom', 'Salle de Bain')
;

INSERT INTO T_PRODUCT(id, tenant_id, account_id, category_fk, sub_category_fk, type, title, summary, description, price, comparable_price, currency, visible, is_deleted, total_views, score)
    VALUES
        (100, 1, 1, 100, 101, 0, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 5, 0.001),
        (101, 1, 1, 100, 102, 0, 'Yo', 'Man', 'This is the description', 1100, 1500, 'XAF', true, false, 100, 0.1),
        (102, 1, 2, 100, 102, 0, 'Yo', 'Man', 'This is the description', 1200, 1500, 'XAF', true, false, 90, 0.01),
        (109, 1, 1, 100, 102, 0, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true, 0, 0.0),
        (110, 1, 1, 100, 102, 1, 'Digital', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 0.0),

        (200, 1, 2, 200, 201, 0, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 90, 0.5),
        (201, 1, 2, 200, 202, 0, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 0.0),

        (300, 1, 3, 200, 201, 0, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, false, 0, 0.0),

        (500, 1, 5, 200, 201, 0, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, false, 0, 0.0),
        (501, 1, 5, 200, 201, 0, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, false, 0, 0.0),
        (502, 1, 5, 200, 201, 0, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, false, 0, 0.0),

        (900, 1, 9, 200, 102, 0, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true, 0, 0.0),

        (2100, 2, 10, 200, 202, 0, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true, 0, 0.0),
        (2200, 2, 10, 200, 201, 0, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true, 0, 0.0)
;

INSERT INTO T_PICTURE(id, product_fk, url, is_deleted, deleted)
    VALUES
        (100, 100, 'https://img.com/1.png', false, null),
        (101, 100, 'https://img.com/2.png', false, null),
        (102, 100, 'https://img.com/3.png', true, now());

UPDATE T_PRODUCT SET thumbnail_fk=100;

INSERT INTO T_SECTION(id, tenant_id, account_id, title, sort_order, is_deleted, deleted)
    VALUES
        (100, 1, 1, 'Yo', 1, false, null),
        (200, 1, 1, 'Man', 1, false, null),
        (900, 1, 1, 'Electronic', 2, true, now())
;

INSERT INTO T_SECTION_PRODUCT(product_fk, section_fk)
    VALUES
        (100, 100),
        (101, 100),
        (109, 100)
    ;

