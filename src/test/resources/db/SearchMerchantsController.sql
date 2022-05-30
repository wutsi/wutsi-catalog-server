INSERT INTO T_MERCHANT(id, tenant_id, account_id, is_enabled, city_id)
    VALUES
        (100, 1, 1, true, 111),
        (200, 1, 2, true, 111),
        (300, 1, 3, true, 222),
        (500, 1, 5, false, null),
        (1000, 10, 10, false, null)
;

INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (100, null, 'Electronic', 'Électronique'),
        (101, 100, 'Cell Phone', 'Téléphone portable'),
        (102, 100, 'TV', 'TV'),

        (200, null, 'Home', 'Maison'),
        (201, 200, 'Living Room', 'Salon'),
        (202, 200, 'Bathroom', 'Salle de Bain')
;

INSERT INTO T_PRODUCT(id, tenant_id, account_id, status, category_fk, sub_category_fk, title, summary, description, price, comparable_price, currency, visible, is_deleted, total_views, score)
    VALUES
        (100, 1, 1, 1, 100, 101, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 5, 0.001),
        (101, 1, 1, 1, 100, 102, 'Yo', 'Man', 'This is the description', 1100, 1500, 'XAF', true, false, 100, 0.1),
        (102, 1, 1, 1, 100, 102, 'Yo', 'Man', 'This is the description', 1200, 1500, 'XAF', true, false, 90, 0.01),
        (109, 1, 1, 1, 100, 102, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, true, 0, 0.0),

        (200, 1, 2, 0, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 90, 0.5),
        (201, 1, 2, 0, 200, 202, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', true, false, 0, 0.0),
        (209, 1, 2, 1, 200, 202, 'Yo', 'Man', 'DELETED - This is the description', 1000, 1500, 'XAF', true, true, 0, 0.0),

        (300, 1, 3, 1, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, false, 0, 0.0),

        (500, 1, 5, 1, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, false, 0, 0.0),
        (501, 1, 5, 1, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, false, 0, 0.0),
        (502, 1, 5, 1, 200, 201, 'Yo', 'Man', 'This is the description', 1000, 1500, 'XAF', false, false, 0, 0.0)
;
