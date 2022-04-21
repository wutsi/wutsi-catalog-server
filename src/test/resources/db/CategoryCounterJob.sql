INSERT INTO T_CATEGORY(id, parent_fk, title, title_french, product_count, published_product_count, update_counters)
    VALUES
        (100, null, 'Electronic', 'Électronique', 111, 11, true),
        (101, 100, 'Cell Phone', 'Téléphone portable', 222, 22, true),
        (102, null, 'Accessories', 'Électronique', 333, 33, true),
        (200, null, 'Books', 'Books', 444, 44, true),
        (201, 200, 'Comics', 'BD', 555, 55, false)
;


INSERT INTO T_PRODUCT(id, status, tenant_id, account_id, category_fk, sub_category_fk, title, summary, description, price, comparable_price, currency, visible, is_deleted, type, quantity, max_order, updated)
    VALUES
        (100, 1, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5, now() - INTERVAL '1 day'),
        (101, 1, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5, now() - INTERVAL '1 day'),
        (102, 0, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5, now() - INTERVAL '1 day'),
        (109, 0, 1, 1, 100, 102, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true, 1, 100, null, now() - INTERVAL '1 day'),
        (200, 1, 1, 1, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5, now() + INTERVAL '1 day')
;
