-- Reset categories
INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (1600, null, 'Fashion', 'Mode'),

        (1610, 1600, 'Men''s Clothing', 'Vêtements pour Hommes'),
        (1611, 1600, 'Men''s Underwear', 'Sous-vêtements pour Hommes'),
        (1620, 1600, 'Women''s Clothing', 'Vêtements pour Femmes'),
        (1621, 1600, 'Women''s Underwear', 'Sous-vêtements pour Femmes'),
        (1630, 1600, 'Kid''s Clothing', 'Vêtements pour Enfants'),
        (1640, 1600, 'Men''s Shoes', 'Souliers pour Hommes'),
        (1650, 1600, 'Women''s Shoes', 'Souliers pour Femmes'),
        (1660, 1600, 'Kid''s Shoes', 'Souliers pour Enfants'),
        (1670, 1600, 'Men''s Accessories', 'Accessoires pour Homes'),
        (1680, 1600, 'Women''s Accessories', 'Accessoires pour Femmes')
    ;
