UPDATE T_PRODUCT SET thumbnail_fk = NULL;
DELETE FROM T_PICTURE;
DELETE FROM T_PRODUCT;

UPDATE T_CATEGORY set parent_fk=null;
DELETE FROM T_CATEGORY;
