INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable'),
        (102, 100, 'TV', 'TV')
;

INSERT INTO T_PRODUCT(id, tenant_id, account_id, category_fk, sub_category_fk, title, price, comparable_price, currency, visible, is_deleted, total_views, total_shares, total_chats, total_sales, total_orders)
    VALUES
        (100, 1, 1, 100, 101, 'Yo', 1000, 1500, 'XAF', true, false, 1000, 100, 10, 50000, 33),
        (101, 1, 1, 100, 102, 'Yo', 1100, 1500, 'XAF', true, false, 100, 10, 1, 15000, 3),
        (109, 1, 1, 100, 102, 'Yo', 1100, 1500, 'XAF', true, true, 10, 10, 1, 10000, 7),
        (200, 1, 2, 100, 102, 'Yo', 1100, 1500, 'XAF', true, false, 100, 10, 1, 15000, 5)
;

INSERT INTO T_MERCHANT(id, tenant_id, account_id, is_enabled, total_views, total_shares, total_chats, total_sales, total_orders)
    VALUES
        (100, 1, 1, true, 31, 32, 33, 34, 35),
        (200, 1, 2, true, 11, 12, 13, 14, 15),
        (300, 1, 3, true, 1, 2, 3, 4, 5)
    ;

