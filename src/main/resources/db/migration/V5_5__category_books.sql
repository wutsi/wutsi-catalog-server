-- Reset categories
INSERT INTO T_CATEGORY(id, parent_fk, title, title_french)
    VALUES
        (1500, null, 'Books', 'Livres'),

        (1510, 1500, 'Comics', 'Bandes Dessinées'),
        (1520, 1500, 'Art & Music', 'Art & Musique'),
        (1521, 1500, 'Biographies', 'Biographies'),
        (1522, 1500, 'Business', 'Affaires'),
        (1523, 1500, 'Computer & Tech', 'Ordinateur & Technologie'),
        (1524, 1500, 'Cooking', 'Cuisine'),
        (1525, 1500, 'Entertainment', 'Entertainment'),
        (1526, 1500, 'Gay & Lesbian', 'Gay & Lesbienne'),
        (1527, 1500, 'Health & Fitness', 'Santé & Forme'),
        (1528, 1500, 'History', 'Histoire'),
        (1529, 1500, 'Hobbies & Craft', 'Loisirs & Artisanat'),
        (1530, 1500, 'Horror', 'Horreur'),
        (1531, 1500, 'Kids', 'Jeunesse'),
        (1532, 1500, 'Fiction', 'Fiction'),
        (1533, 1500, 'Medical', 'Médical'),
        (1534, 1500, 'Mysteries', 'Mystères'),
        (1535, 1500, 'Parenting', 'Parentalité'),
        (1536, 1500, 'Religion', 'Religion'),
        (1537, 1500, 'Romance', 'Romance'),
        (1538, 1500, 'Science Fiction', 'Science Fiction'),
        (1539, 1500, 'Science & Math', 'Sciences & Mathématiques'),
        (1540, 1500, 'Social Science', 'Science Sociales'),
        (1541, 1500, 'Sports', 'Sports'),
        (1542, 1500, 'Travel', 'Voyages'),
        (1543, 1500, 'Teen', 'Adolescent')
    ;
