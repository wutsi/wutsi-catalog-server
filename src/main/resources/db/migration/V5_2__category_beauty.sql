-- Reset categories
INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (1200, null, 'Beauty', 'Beauty'),

        (1210, 1200, 'Makeup', 'Maquillage'),
        (1220, 1200, 'Nails', 'Ongles'),
        (1230, 1200, 'Skin Care', 'Soins de Peau'),
        (1240, 1200, 'Hair Care', 'Soins de Cheveux'),
        (1241, 1200, 'Wigs', 'Perruques'),
        (1242, 1200, 'Hair Extensions', 'Extensions de Cheuveux'),
        (1250, 1200, 'Oral Care', 'Soins de Bouche'),
        (1260, 1200, 'Fragrance', 'Fragrance')
    ;
