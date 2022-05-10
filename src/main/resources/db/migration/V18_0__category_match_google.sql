-- Beauty
UPDATE T_CATEGORY SET title='Health & Beauty', title_french='Santé & Beauté' WHERE id=1200;

-- Home
UPDATE T_CATEGORY SET title='Home & Garden', title_french='Maison & Jardin' WHERE id=1400;

-- Apparel
UPDATE T_CATEGORY SET title='Apparel & Accessories', title_french='Vêtements & Accessoires' WHERE id=1600;

-- Electronics
UPDATE T_CATEGORY set title='Televisions', title_french='Télévisions' WHERE id=1140;

-- Computers
UPDATE T_CATEGORY set title='Desktop Computers' WHERE id=1710;
UPDATE T_CATEGORY set title='Tablet Computers' WHERE id=1730;
UPDATE T_CATEGORY set title='Computer Monitor', title_french='Moniteur d''Ordinateur' WHERE id=1740;
UPDATE T_CATEGORY set title='Networking', title_french='Réseautique' WHERE id=1760;
INSERT INTO T_CATEGORY (id, parent_fk, title, title_french) VALUES (1770, 1700, 'Computer Servers', 'Serveurs informatiques');

-- Food Items
UPDATE T_CATEGORY SET title='Food Items', title_french='Produits Alimentaire' WHERE id=2100;

-- Cell phone
UPDATE T_CATEGORY SET title='Mobile Phones & Accessories' WHERE id=1800;
UPDATE T_CATEGORY SET title='Mobile Phones' WHERE id=1850;
UPDATE T_CATEGORY SET title='Power Adapter & Charger', title_french='Adaptateurs & Chargeurs' WHERE id=1854;
UPDATE T_CATEGORY SET title='Mobile Phone Accessories' WHERE id=1860;
