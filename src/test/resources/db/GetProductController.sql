INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable')
;


INSERT INTO T_PRODUCT(id, tenant_id, account_id, category_fk, sub_category_fk, title, summary, description, price, comparable_price, currency, visible, is_deleted, type, quantity, max_order)
    VALUES
        (100, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 100, 5),
        (900, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true, 1, 100, null)
;

INSERT INTO T_PICTURE(id, product_fk, url, is_deleted, deleted)
    VALUES
        (100, 100, 'https://img.com/1.png', false, null),
        (101, 100, 'https://img.com/2.png', false, null),
        (102, 100, 'https://img.com/3.png', true, now());

UPDATE T_PRODUCT SET thumbnail_fk=100;

INSERT INTO T_SECTION(id, tenant_id, account_id, title, sort_order, product_count, is_deleted, deleted)
    VALUES
        (100, 1, 1, 'Yo', 1, 2, false, null),
        (200, 1, 1, 'Man', 1, 2, false, null),
        (900, 1, 1, 'Electronic', 2, 11, true, now())
;

INSERT INTO T_SECTION_PRODUCT(product_fk, section_fk)
    VALUES
        (100, 100),
        (100, 200),
        (100, 900)
    ;

