INSERT INTO T_PRODUCT(id, tenant_id, account_id, category_id, title, summary, description, price, comparable_price, currency, visible, is_deleted)
    VALUES
        (100, 1, 1, 100, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),
        (101, 1, 1, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),
        (109, 1, 1, 100, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true),

        (200, 1, 2, 100, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),

        (300, 1, 3, 100, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, false),

        (900, 1, 9, null, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true),

        (2100, 2, 10, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true),
        (2200, 2, 10, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true)
;

INSERT INTO T_PICTURE(id, product_fk, url, is_deleted, deleted)
    VALUES
        (100, 100, 'https://img.com/1.png', false, null),
        (101, 100, 'https://img.com/2.png', false, null),
        (102, 100, 'https://img.com/3.png', true, now());

UPDATE T_PRODUCT SET thumbnail_fk=100
