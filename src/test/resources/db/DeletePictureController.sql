INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable')
;

INSERT INTO T_PRODUCT(id, tenant_id, account_id, category_fk, sub_category_fk, title, summary, description, price, comparable_price, currency, visible, is_deleted)
    VALUES
        (100, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),
        (200, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),
        (300, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),
        (900, 1, 2, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true)
;

INSERT INTO T_PICTURE(id, product_fk, url, is_deleted, deleted)
    VALUES
        (100, 100, 'https://img.com/1.png', false, null),
        (101, 100, 'https://img.com/2.png', false, null),
        (102, 100, 'https://img.com/3.png', false, null),
        (109, 100, 'https://img.com/4.png', true, now()),

        (200, 200, 'https://img.com/200.png', false, null),
        (201, 200, 'https://img.com/201.png', true, now()),
        (202, 200, 'https://img.com/201.png', false, null),

        (300, 300, 'https://img.com/300.png', false, null),

        (900, 900, 'https://img.com/900.png', true, now())
;

UPDATE T_PRODUCT SET thumbnail_fk=102 WHERE id=100;
UPDATE T_PRODUCT SET thumbnail_fk=200 WHERE id=200;
UPDATE T_PRODUCT SET thumbnail_fk=300 WHERE id=300;


