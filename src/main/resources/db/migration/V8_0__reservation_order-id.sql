ALTER TABLE T_RESERVATION DROP COLUMN order_id;
ALTER TABLE T_RESERVATION ADD COLUMN order_id VARCHAR(36) NOT NULL;
