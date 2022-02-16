INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (1900, null, 'Bakery', 'Boulangerie'),

        (1910, 1900, 'Bread', 'Pain'),
        (1920, 1900, 'Pastries', 'Pastries'),
        (1930, 1900, 'Viennoiseries', 'Viennoiseries'),
        (1940, 1900, 'Beverages', 'Boissons'),
        (1950, 1900, 'Ice Cream', 'Glaceries'),
        (1960, 1900, 'Sandwiches', 'Sandwiches'),
        (1970, 1900, 'Salads', 'Salades'),
        (1980, 1900, 'Ready to Eat', 'Mets Cuisinés')
    ;
