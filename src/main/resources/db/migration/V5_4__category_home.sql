-- Reset categories
INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (1400, null, 'Home', 'Maison'),

        (1410, 1300, 'Kitchen', 'Cuisine'),
        (1411, 1300, 'Appliance', 'Électro-Ménager'),
        (1412, 1300, 'Tableware', 'Vaisselles'),
        (1413, 1300, 'Cookware', 'Ustenciles de Cuisine'),
        (1420, 1300, 'Furniture', 'Meubles'),
        (1430, 1300, 'Heating, Cooling', 'Chauffage, Refroidissmeent'),
        (1440, 1300, 'Vacuum and Floor Care', 'Aspirateur et Entretien du Plancher'),
        (1450, 1300, 'Home Decor', 'Décoration de Maison'),
        (1460, 1300, 'Bedding', 'Literie'),
        (1470, 1300, 'Bathroom', 'Salle de Bain'),
        (1480, 1300, 'Storage and Organization', 'Rangement et Organisation'),
        (1490, 1300, 'Home Office', 'Bureau à Domicile')
    ;
