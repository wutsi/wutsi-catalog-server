INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable'),
        (102, 100, 'TV', 'TV'),

        (200, null, 'Home', 'Maison'),
        (201, 200, 'Living Room', 'Salon'),
        (202, 200, 'Bathroom', 'Salle de Bain')
;

INSERT INTO T_PRODUCT(id, status, tenant_id, account_id, category_fk, sub_category_fk, title, summary, description, price, comparable_price, currency, is_deleted, total_views, score, quantity)
    VALUES
        (100, 1, 1, 1, 100, 101, 'Product 100', 'This is the summary 100', 'This is the description', 1000, 1500, 'XAF', false, 5, 0.001, 100),
        (101, 1, 1, 1, 100, 102, 'Product 101', 'This is the summary 101', 'This is the description', 1100, null, 'XAF', false, 100, 0.1, 0),
        (102, 0, 1, 2, 100, 102, 'Product 102', 'This is the summary 102', 'This is the description', 1200, null, 'XAF', false, 90, 0.01, 10),
        (109, 1, 1, 1, 100, 102, 'Product 109', 'This is the summary 109', 'This is the description', 1000, null, 'XAF', true, 0, 0.0, 55),

        (200, 1, 1, 2, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, 90, 0.5, 0),
        (201, 1, 1, 2, 200, 202, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, 0, 0.0, 0),

        (300, 1, 1, 3, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, 0, 0.0, 0),

        (900, 1, 1, 9, 200, 102, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, 0, 0.0, 0),

        (2100, 1, 2, 10, 200, 202, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, 0, 0.0, 0),
        (2200, 1, 2, 10, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, 0, 0.0, 0)
;

INSERT INTO T_PICTURE(id, product_fk, url, is_deleted, deleted)
    VALUES
        (100, 100, 'https://img.com/1.png', false, null),
        (101, 100, 'https://img.com/2.png', false, null),
        (102, 100, 'https://img.com/3.png', true, now());

UPDATE T_PRODUCT SET thumbnail_fk=100;

INSERT INTO T_SECTION(id, tenant_id, account_id, title, sort_order, product_count, is_deleted, deleted)
    VALUES
        (100, 1, 1, 'Yo', 1, 2, false, null),
        (200, 1, 1, 'Man', 1, 2, false, null),
        (900, 1, 1, 'Electronic', 2, 11, true, now())
;

INSERT INTO T_SECTION_PRODUCT(product_fk, section_fk)
    VALUES
        (100, 100),
        (101, 100),
        (109, 100)
    ;

