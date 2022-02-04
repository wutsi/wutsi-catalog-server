INSERT INTO T_CATEGORY(id, tenant_id, account_id, title, is_deleted)
    VALUES
        (100, 1, 1,  'Yo', false),
        (110, 1, 1,  'Man', false),
        (200, 1, 1,  'Another user', false),
        (900, 1, 1,  'XXX', true)
;

INSERT INTO T_PRODUCT(id, tenant_id, account_id,  title, summary, description, price, comparable_price, currency, visible, is_deleted)
    VALUES
        (100, 1, 1, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),
        (200, 1, 1, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true)
;

INSERT INTO T_PRODUCT_CATEGORY(product_fk, category_fk)
    VALUES
        (200, 200);
