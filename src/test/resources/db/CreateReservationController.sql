INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable'),
        (102, 100, 'TV', 'TV'),

        (200, null, 'Home', 'Maison'),
        (201, 200, 'Living Room', 'Salon')
;

INSERT INTO T_PRODUCT(id, tenant_id, account_id, category_fk, sub_category_fk, title, summary, description, price, comparable_price, currency, visible, is_deleted, quantity)
    VALUES
        (100, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 100),
        (101, 1, 2, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 1),
        (200, 1, 2, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 100),
        (201, 1, 2, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 1)
;
