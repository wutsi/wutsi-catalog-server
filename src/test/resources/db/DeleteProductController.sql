INSERT INTO T_CATEGORY(id, parent_fk, title, title_french, update_counters)
    VALUES
        (100, null, 'Electronic', 'Électronique', false),
        (101, 100, 'Cell Phone', 'Téléphone portable', false)
;

INSERT INTO T_PRODUCT(id, tenant_id, account_id, category_fk, sub_category_fk, title, summary, description, price, comparable_price, currency, visible, is_deleted)
    VALUES
        (100, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false),
        (900, 1, 2, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true)
;

INSERT INTO T_SECTION(id, tenant_id, account_id, title, sort_order, is_deleted, deleted, update_counters)
    VALUES
        (100, 1, 1, 'Yo', 1, false, null, false),
        (200, 1, 1, 'Man', 1, false, null, false)
;

INSERT INTO T_SECTION_PRODUCT(product_fk, section_fk)
    VALUES
        (100, 100),
        (100, 200)
    ;
