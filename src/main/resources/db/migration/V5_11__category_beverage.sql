INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (2000, null, 'Beverages', 'Boissons'),

        (2010, 2000, 'Watter', 'Eau'),
        (2020, 2000, 'Hot Beverage', 'Boisson Chaudes'),
        (2021, 2000, 'The', 'Thé'),
        (2022, 2000, 'Coffee', 'Café'),
        (2030, 2000, 'Juices', 'Jus'),
        (2031, 2000, 'Soft Drinks', 'Boissons Gazeuses'),
        (2032, 2000, 'Energy & Sport Drinks', 'Boissons Énergisantes & Sportives'),
        (2040, 2000, 'Alcoholic beverages', 'Boissons Alcoolisées'),
        (2041, 2000, 'Wine', 'Vin'),
        (2042, 2000, 'Spirit', 'Spiritieux'),
        (2043, 2000, 'Champagnes', 'Champagnes'),
        (2044, 2000, 'Beers', 'Bières'),
        (2045, 2000, 'Aperitifs ', 'Aperitifs')
    ;
