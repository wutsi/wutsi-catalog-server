INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable')
;


INSERT INTO T_PRODUCT(id, status, tenant_id, account_id, category_fk, sub_category_fk, title, summary, description, price, comparable_price, currency, visible, is_deleted, type, quantity, max_order, updated)
    VALUES
        (100, 1, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5, now() - INTERVAL '1 day'),
        (101, 1, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5, now() - INTERVAL '1 day'),
        (102, 0, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5, now() - INTERVAL '1 day'),
        (103, 0, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5, now() + INTERVAL '1 day'),
        (109, 0, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true, 1, 100, null, now() - INTERVAL '1 day'),
        (900, 0, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true, 1, 100, null, now() - INTERVAL '1 day')
;


INSERT INTO T_SECTION(id, tenant_id, account_id, title, sort_order, is_deleted, deleted, product_count, published_product_count, update_counters)
    VALUES
        (100, 1, 1, 'Yo', 1, false, null, 555, 55, true),
        (200, 1, 1, 'Man', 1, false, null, 666, 66, false),
        (300, 1, 1, 'Man', 1, false, null, 777, 77, true),
        (900, 1, 1, 'Electronic', 2, true, now(), 100, 10, true)
;

INSERT INTO T_SECTION_PRODUCT(product_fk, section_fk)
    VALUES
        (100, 100),
        (101, 100),
        (102, 100),
        (109, 100),
        (900, 100),

        (100, 200),
        (103, 200),
        (109, 200)
    ;

