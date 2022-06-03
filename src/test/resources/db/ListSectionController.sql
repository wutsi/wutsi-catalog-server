INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable')
;


INSERT INTO T_PRODUCT(id, tenant_id, account_id, status, category_fk, sub_category_fk, title, summary, description, price, comparable_price, currency, visible, is_deleted, type, quantity, max_order, total_views, total_orders, total_sales, conversion, score, total_shares, total_chats)
    VALUES
        (100, 1, 1, 1, 100, 101, 'Yo', 'Man', 'PUBLISHED - This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5, 1000, 10, 150000, 0.01, .5, 11, 15),
        (101, 1, 1, 1, 100, 101, 'Yo', 'Man', 'PUBLISHED - This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5, 1000, 10, 150000, 0.01, .5, 11, 15),
        (110, 1, 1, 0, 100, 101, 'Yo', 'Man', 'DRAFT - This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5, 1000, 10, 150000, 0.01, .5, 11, 15),
        (900, 1, 1, 1, 100, 101, 'Yo', 'Man', 'DELETED - This is the description', 1000, 1500, 'XAF', true, true, 1, 100, null, 1000, 10, 150000, 0.1, .7, 0, 0)
;


INSERT INTO T_SECTION(id, tenant_id, account_id, title, sort_order, is_deleted, deleted)
    VALUES
        (100, 1, 1, 'Electronic', 2, false, null),
        (110, 1, 1, 'Deals', 1, false, null),
        (120, 1, 1, 'New Stuff', 3, false, null),
        (900, 1, 1, 'Deleted', 2, true, now()),
        (200, 1, 2, 'Women', 1, false, null)
;

INSERT INTO T_SECTION_PRODUCT(section_fk, product_fk)
    VALUES
        (100, 100),
        (100, 101),
        (110, 100),
        (110, 110),
        (110, 900)
    ;
