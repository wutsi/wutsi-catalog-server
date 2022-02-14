-- Reset categories
INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (1100, null, 'Electronics', 'Électronique'),

        (1110, 1100, 'Computers', 'Ordinateurs'),
        (1111, 1100, 'Laptops', 'Portables'),
        (1112, 1100, 'Tablets', 'Tablettes'),
        (1114, 1100, 'Monitors', 'Écrans'),
        (1115, 1100, 'Printers', 'Imprimantes'),
        (1116, 1100, 'Routers', 'Routeurs'),
        (1117, 1100, 'Projectors', 'Projecteurs'),
        (1130, 1100, 'Cameras', 'Appareil Photos'),
        (1140, 1100, 'TV', 'TV'),
        (1141, 1100, 'Speakers', 'Haut-Parleurs'),
        (1150, 1100, 'Cell Phones', 'Téléphones portables'),
        (1151, 1100, 'Headphones', 'Écouteurs'),
        (1152, 1100, 'Cases and Covers', 'Étuis et Jousses'),
        (1153, 1100, 'SIM Cards', 'Cartes SIM'),
        (1154, 1100, 'Chargers', 'Chargeurs'),
        (1160, 1100, 'Accessories', 'Accessoires')
    ;
