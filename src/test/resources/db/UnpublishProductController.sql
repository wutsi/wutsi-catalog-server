INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable')
;


INSERT INTO T_PRODUCT(id, type, tenant_id, account_id, category_fk, sub_category_fk, status, title, summary, description, price, comparable_price, currency, visible, is_deleted, quantity, max_order, numeric_file_url)
    VALUES
        (100, 0, 1, 1, 100, 101, 1, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 100, 5, null),
        (110, 0, 1, 1, 100, 101, 1, '', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 100, 5, null),
        (111, 1, 1, 1, 100, 101, 1, 'No File URL', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 100, 5, null),
        (112, 0, 1, 1, 100, 101, 1, 'No Picture', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 100, 5, null),
        (900, 0, 1, 1, 100, 101, 1, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true, 100, null, null)
;
