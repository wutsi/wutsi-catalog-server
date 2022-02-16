INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (1700, null, 'Computers', 'Informatique'),

        (1710, 1700, 'Desktops', 'Ordinateurs de Bureaux'),
        (1711, 1700, 'Computer Accessories', 'Accessoires d''Ordinateur'),
        (1720, 1700, 'Laptops', 'Portables'),
        (1730, 1700, 'Tablets', 'Tablettes'),
        (1740, 1700, 'Monitors', 'Écrans'),
        (1750, 1700, 'Printers', 'Imprimantes'),
        (1751, 1700, 'Printer Accessories', 'Accessoires d''Imprimante'),
        (1760, 1700, 'Network Products', 'Produits Réseau')
    ;

-- Remove Computer categories from Eletronics
DELETE FROM T_CATEGORY WHERE id>=1110 AND id<1120;
