DELETE FROM T_MERCHANT;

DELETE FROM T_SECTION_PRODUCT;
DELETE FROM T_SECTION;

DELETE FROM T_RESERVATION_PRODUCT;
DELETE FROM T_RESERVATION;

UPDATE T_PRODUCT SET thumbnail_fk = NULL;
DELETE FROM T_PICTURE;
DELETE FROM T_PRODUCT;

UPDATE T_CATEGORY set parent_fk=null;
DELETE FROM T_CATEGORY;
