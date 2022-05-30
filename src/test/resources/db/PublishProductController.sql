INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable')
;


INSERT INTO T_PRODUCT(id, type, tenant_id, account_id, category_fk, sub_category_fk, title, summary, description, price, comparable_price, currency, visible, is_deleted, quantity, max_order, numeric_file_url)
    VALUES
        (100, 0, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 100, 5, null),
        (110, 0, 1, 1, 100, 101, '', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 100, 5, null),
        (111, 1, 1, 1, 100, 101, 'No File URL', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 100, 5, null),
        (112, 0, 1, 1, 100, 101, 'No Picture', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 100, 5, null),
        (113, 0, 1, 1, 100, 101, 'No Summary', null, 'This is the description', 1000, 1500, 'XAF', true, false, 100, 5, null),
        (900, 0, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true, 100, null, null)
;

INSERT INTO T_PICTURE(id, product_fk, url, is_deleted, deleted)
    VALUES
        (1000, 100, 'https://img.com/1.png', false, null),
        (1100, 110, 'https://img.com/2.png', false, null),
        (1110, 111, 'https://img.com/3.png', false, null),
        (1111, 111, 'https://img.com/3.png', true, now()),
        (1129, 112, 'https://img.com/4.png', true, now());

INSERT INTO T_SECTION(id, tenant_id, account_id, title, sort_order, is_deleted, deleted)
    VALUES
        (100, 1, 1, 'Yo', 1, false, null),
        (200, 1, 1, 'Man', 1, false, null)
;

INSERT INTO T_SECTION_PRODUCT(product_fk, section_fk)
    VALUES
        (100, 100),
        (100, 200)
    ;
