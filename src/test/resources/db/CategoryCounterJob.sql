INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable'),
        (102, null, 'Accessories', 'Électronique'),
        (200, null, 'Books', 'Books'),
        (201, 200, 'Comics', 'BD')
;


INSERT INTO T_PRODUCT(id, status, tenant_id, account_id, category_fk, sub_category_fk, title, summary, description, price, comparable_price, currency, visible, is_deleted, type, quantity, max_order)
    VALUES
        (100, 1, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5),
        (101, 1, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5),
        (102, 0, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5),
        (109, 0, 1, 1, 100, 102, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true, 1, 100, null),
        (200, 1, 1, 1, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5)
;
