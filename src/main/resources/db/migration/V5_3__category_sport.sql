-- Reset categories
INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (1300, null, 'Sport', 'Sports'),

        (1310, 1300, 'Exercice and Fitness Equipment', 'Équipement d''exercice et de fitness'),
        (1320, 1300, 'Indoor Games', 'Jeux d''intérieur'),
        (1330, 1300, 'Outdoor Games', 'Jeux d''extérieur'),
        (1340, 1300, 'Sport Equipments', 'Équipement de sport')
    ;
