INSERT INTO T_CATEGORY(id, tenant_id, account_id, title, is_deleted)
    VALUES
        (100, 1, 1,  'Yo', false),
        (110, 1, 1,  'Man', false),
        (200, 1, 2,  'Another user', false),
        (900, 1, 1,  'XXX', true)
;

INSERT INTO T_PRODUCT(id, tenant_id, account_id, title, summary, description, price, comparable_price, currency, visible, is_deleted)
    VALUES
        (100, 1, 1, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),
        (900, 1, 1, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true)
;

INSERT INTO T_PICTURE(id, product_fk, url, is_deleted, deleted)
    VALUES
        (100, 100, 'https://img.com/1.png', false, null),
        (101, 100, 'https://img.com/2.png', false, null),
        (102, 100, 'https://img.com/3.png', true, now());

UPDATE T_PRODUCT SET thumbnail_fk=100;

INSERT INTO T_PRODUCT_CATEGORY(product_fk, category_fk)
    VALUES
        (100, 100),
        (100, 110),
        (100, 900);
