INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (1800, null, 'Cell Phones & Accessories', 'Téléphones Portables & Accessoires'),

        (1850, 1800, 'Cell Phones', 'Téléphones Portables'),
        (1851, 1800, 'Headphone & Earphone', 'Casques & Écouteurs'),
        (1852, 1800, 'Cases & Covers', 'Étuis & Housses'),
        (1854, 1800, 'Chargers', 'Chargeurs'),
        (1860, 1800, 'Mobile Accessories', 'Accessoires Mobile')
    ;

-- Remove Cellphone from Eletronics
DELETE FROM T_CATEGORY WHERE id>=1150 AND id<1160;
