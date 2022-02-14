-- Reset categories
INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (1400, null, 'Home', 'Maison'),

        (1410, 1400, 'Kitchen', 'Cuisine'),
        (1411, 1400, 'Appliance', 'Électro-Ménager'),
        (1412, 1400, 'Tableware', 'Vaisselles'),
        (1414, 1400, 'Cookware', 'Ustenciles de Cuisine'),
        (1420, 1400, 'Furniture', 'Meubles'),
        (1430, 1400, 'Heating, Cooling', 'Chauffage, Refroidissmeent'),
        (1440, 1400, 'Vacuum and Floor Care', 'Aspirateur et Entretien du Plancher'),
        (1450, 1400, 'Home Decor', 'Décoration de Maison'),
        (1460, 1400, 'Bedding', 'Literie'),
        (1470, 1400, 'Bathroom', 'Salle de Bain'),
        (1480, 1400, 'Storage and Organization', 'Rangement et Organisation'),
        (1490, 1400, 'Home Office', 'Bureau à Domicile')
    ;
