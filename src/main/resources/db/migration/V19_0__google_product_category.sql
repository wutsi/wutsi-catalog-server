ALTER TABLE T_CATEGORY ADD COLUMN google_product_category_id BIGINT;

-- Electronics
UPDATE T_CATEGORY set google_product_category_id=222 WHERE id=1100;
UPDATE T_CATEGORY set google_product_category_id=249 WHERE id=1141;
UPDATE T_CATEGORY set google_product_category_id=404 WHERE id=1140;
UPDATE T_CATEGORY set google_product_category_id=2082 WHERE id=1160;
UPDATE T_CATEGORY set google_product_category_id=345, title='Print, Copy, Scan & Fax', title_french='Imprimantes, Photocopies, Scanners & Fax' WHERE id=1110;
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1120, 1100, 1420, 'Audio Accessories', 'Accessoires Audio');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1121, 1100, 505771, 'Headphones & Headsets', 'Écouteurs & Casques');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1122, 1100, 234, 'Microphones', 'Micros');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1123, 1100, 239, 'Speakers', 'Haut-parleurs');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1124, 1100, 242, 'Audio Players & Recorders', 'Lecteurs & Enregistreurs Audio');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1161, 1100, 505295, 'Power Adapter & Charger', 'Adaptateurs & Chargeurs');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1199, 1100, null, 'Other', 'Autre');

-- Health & Beauty
UPDATE T_CATEGORY set google_product_category_id=469 WHERE id=1200;
UPDATE T_CATEGORY set google_product_category_id=477 WHERE id=1210;
UPDATE T_CATEGORY set google_product_category_id=478, title='Nail Care', title_french='Manucure' WHERE id=1220;
UPDATE T_CATEGORY set google_product_category_id=567  WHERE id=1230;
UPDATE T_CATEGORY set google_product_category_id=486  WHERE id=1240;
UPDATE T_CATEGORY set google_product_category_id=181  WHERE id=1241;
UPDATE T_CATEGORY set google_product_category_id=4057  WHERE id=1242;
UPDATE T_CATEGORY set google_product_category_id=526  WHERE id=1250;
UPDATE T_CATEGORY set google_product_category_id=479  WHERE id=1260;
UPDATE T_CATEGORY set google_product_category_id=494, title='Health Care', title_french='Soins de Santé'  WHERE id=1270;
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1299, 1200, null, 'Other', 'Autre');

-- Sporting Goods/Articles de Sport
UPDATE T_CATEGORY set google_product_category_id=988, title='Sporting Goods', title_french='Articles de Sports' WHERE id=1300;
DELETE FROM T_CATEGORY WHERE parent_fk=1300;
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1310, 1300, 1111, 'Football', 'Football');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1311, 1300, 1081, 'Basketball', 'Basketball');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1312, 1300, 1083, 'Boxing & Martial Arts', 'Boxe & Arts Martiaux');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1313, 1300, 1000, 'Gymnastics', 'Gymnastique');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1314, 1300, 1065, 'Tennis', 'Tennis');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1315, 1300, 1068, 'Track & Field', 'Couse de piste');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1316, 1300, 1115, 'Volleyball', 'Volleyball');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1317, 1300, 990, 'Exercise & Fitness', 'Exercise & Fitness');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1399, 1300, null, 'Other', 'Autre');

-- Home & Garden
UPDATE T_CATEGORY set google_product_category_id=536 WHERE id=1400;
UPDATE T_CATEGORY set google_product_category_id=638 WHERE id=1410;
UPDATE T_CATEGORY set google_product_category_id=604 WHERE id=1411;
UPDATE T_CATEGORY set google_product_category_id=672 WHERE id=1412;
UPDATE T_CATEGORY set google_product_category_id=654 WHERE id=1414;
UPDATE T_CATEGORY set google_product_category_id=499873 WHERE id=1430;
UPDATE T_CATEGORY set title='Floor Cleaners', title_french='Nettoyants pour Planchers', google_product_category_id=4977 WHERE id=1440;
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1441, 1400, 619, 'Vacuums & Accessories', 'Aspirateurs & Accessoires');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1442, 1400, 4973, 'Cleaning Products', 'Produits de Nettoyage');
UPDATE T_CATEGORY set google_product_category_id=696 WHERE id=1450;
UPDATE T_CATEGORY set google_product_category_id=569 WHERE id=1460;
UPDATE T_CATEGORY set google_product_category_id=574 WHERE id=1470;
UPDATE T_CATEGORY set google_product_category_id=636 WHERE id=1480;
DELETE FROM T_CATEGORY where id=1490;
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1499, 1400, null, 'Other', 'Autre');


-- Books
UPDATE T_CATEGORY set google_product_category_id=784 WHERE id=1500;
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1599, 1500, null, 'Other', 'Autre');

-- Apparel & Accessories
UPDATE T_CATEGORY set google_product_category_id=166 WHERE id=1600;
DELETE FROM T_CATEGORY WHERE parent_fk=1600;
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1611, 1600, 204, 'Pants', 'Pantalons');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1612, 1600, 212, 'Shirts and Tops', 'Chemises et Hauts');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1613, 1600, 207, 'Shorts', 'Shorts');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1614, 1600, 1581, 'Skirts', 'Jupes');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1615, 1600, 1594, 'Suits', 'Costumes');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1616, 1600, 211, 'Swimwear', 'Maillots de bain');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1617, 1600, 2562, 'Underwear', 'Sous-vêtement');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1618, 1600, 209, 'Underwear', 'Sous-vêtement');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1619, 1600, 1772, 'Socks', 'Chaussettes');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1620, 1600, 2771, 'Dresses', 'Robes');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1630, 1600, 187, 'Shoes', 'Chaussures');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1640, 1600, 187, 'Jewelry', 'Bijoux');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1650, 1600, 187, 'Clothing Accessories', 'Accessoires');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1660, 1600, 187, 'Sport Uniforms', 'Uniformes de Sport');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1699, 1600, null, 'Other', 'Autre');


-- Mobile Phones & Accessories
UPDATE T_CATEGORY set title='Phone & Accessories', title_french='Téléphones & Accessoires', google_product_category_id=270 WHERE id=1800;
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1810, 1800, 271, 'Corded Phones', 'Téléphones fixes');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1811, 1800, 272, 'Cordless Phones', 'Téléphones sans fils');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1812, 1800, 1924, 'Satellite Phones', 'Téléphones Satellites');
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1813, 1800, 4666, 'Conference Phones', 'Téléphones de Conférence');
DELETE FROM T_CATEGORY WHERE id=1851;
DELETE FROM T_CATEGORY WHERE id=1852;
DELETE FROM T_CATEGORY WHERE id=1854;
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1899, 1800, null, 'Other', 'Autre');

-- Bakery
UPDATE T_CATEGORY set google_product_category_id=1876 WHERE id=1900;
UPDATE T_CATEGORY set google_product_category_id=424 WHERE id=1910;
UPDATE T_CATEGORY set google_product_category_id=5750 WHERE id=1920;
UPDATE T_CATEGORY set google_product_category_id=6195 WHERE id=1930;
UPDATE T_CATEGORY set title='Bakery Assortments', title_french='Assortiments de Boulangerie', google_product_category_id=5904 WHERE id=1940;
UPDATE T_CATEGORY set google_product_category_id=5790 WHERE id=1950;
UPDATE T_CATEGORY set title='Donuts', title_french='Beignets', google_product_category_id=5751 WHERE id=1960;
UPDATE T_CATEGORY set title='Cakes', title_french='Gâteaux', google_product_category_id=2194 WHERE id=1970;
DELETE FROM T_CATEGORY WHERE id=1880;
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (1999, 1900, null, 'Other', 'Autre');

-- Beverage
UPDATE T_CATEGORY set google_product_category_id=413 WHERE id=2000;
UPDATE T_CATEGORY set google_product_category_id=420, title='Water' WHERE id=2010;
DELETE FROM T_CATEGORY WHERE id=2020;
UPDATE T_CATEGORY set google_product_category_id=2073, title='Tee & Infusions', title_french='Thé & Infusions' WHERE id=2021;
UPDATE T_CATEGORY set google_product_category_id=1868 WHERE id=2022;
UPDATE T_CATEGORY set google_product_category_id=2887 WHERE id=2030;
UPDATE T_CATEGORY set google_product_category_id=2868 WHERE id=2031;
UPDATE T_CATEGORY set title='Sports & Energy Drinks', title_french='Boissons Énergisante', google_product_category_id=5723 WHERE id=2032;
UPDATE T_CATEGORY set google_product_category_id=499676 WHERE id=2040;
UPDATE T_CATEGORY set google_product_category_id=421 WHERE id=2041;
UPDATE T_CATEGORY set google_product_category_id=417, title='Liquors & Spirits', title_french='Alcools & Spiritueux' WHERE id=2042;
DELETE FROM T_CATEGORY WHERE id=2043;
UPDATE T_CATEGORY set google_product_category_id=414 WHERE id=2044;
DELETE FROM T_CATEGORY WHERE id=2045;
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (2099, 2000, null, 'Other', 'Autre');


-- Food Items
UPDATE T_CATEGORY set google_product_category_id=422 WHERE id=2100;
DELETE FROM T_CATEGORY WHERE id=2110;
UPDATE T_CATEGORY set google_product_category_id=4628 WHERE id=2120;
UPDATE T_CATEGORY set google_product_category_id=4629 WHERE id=2121;
UPDATE T_CATEGORY set google_product_category_id=428, title='Dairy Products & Eggs', title_french='Produits Laitiers & Oeufs' WHERE id=2130;
UPDATE T_CATEGORY set google_product_category_id=423 WHERE id=2140;
DELETE FROM T_CATEGORY WHERE id=2141;
UPDATE T_CATEGORY set google_product_category_id=431, title='Rice, Grain & Cereal', title_french='Riz, Grain & Céréales' WHERE id=2142;
UPDATE T_CATEGORY set google_product_category_id=434 WHERE id=2143;
UPDATE T_CATEGORY set google_product_category_id=427 WHERE id=2144;
UPDATE T_CATEGORY set google_product_category_id=5740 WHERE id=2145;
UPDATE T_CATEGORY set google_product_category_id=4748 WHERE id=2146;
DELETE FROM T_CATEGORY WHERE id=2150;
DELETE FROM T_CATEGORY WHERE id=2151;
UPDATE T_CATEGORY set google_product_category_id=5795 WHERE id=2160;
UPDATE T_CATEGORY set google_product_category_id=5793 WHERE id=2161;
DELETE FROM T_CATEGORY WHERE id=2170;
DELETE FROM T_CATEGORY WHERE id=2180;
INSERT INTO T_CATEGORY(id, parent_fk, google_product_category_id, title, title_french) VALUES (2199, 2100, null, 'Other', 'Autre');
