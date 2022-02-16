INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (2100, null, 'Food', 'Alimentation'),

        (2110, 2100, 'Breakfast Foods', 'Aliments du Petit-Déjeuner'),
        (2120, 2100, 'Meat', 'Viandes'),
        (2121, 2100, 'Fish & Seafood', 'Poissons & Fruits de Mer'),
        (2130, 2100, 'Dairy & Eggs', 'Produits Laitiers & Oeufs'),
        (2140, 2100, 'Snack Foods', 'Snacks'),
        (2141, 2100, 'Canned Foods', 'Aliments en Conserve'),
        (2142, 2100, 'Grains & Rice', 'Céréales & Riz'),
        (2143, 2100, 'Pasta & Noodles', 'Pâtes & Nouilles'),
        (2144, 2100, 'Spices & Marinades', 'Épices & Assaisonnements'),
        (2145, 2100, 'Jams & Spreads', 'Confitures & Tartinades'),
        (2146, 2100, 'Candies & Chocolates', 'Bonbons & Chocolats'),
        (2150, 2100, 'Frozen Products', 'Produits Surgelés'),
        (2151, 2100, 'Packaged Meals', 'Repas Emballés'),
        (2160, 2100, 'Fruits', 'Fruits'),
        (2161, 2100, 'Vegetables', 'Légumes'),
        (2170, 2100, 'Baby Foods', 'Aliments pour Bébé'),
        (2180, 2100, 'Cleaning Tools', 'Outils de Nettoyage'),
        (2181, 2100, 'Cleaning Products', 'Produits de Nettoyage')
    ;
