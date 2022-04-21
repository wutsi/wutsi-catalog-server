DROP TABLE T_METRIC;
DROP TABLE T_PERIOD;

ALTER TABLE T_PRODUCT ADD COLUMN total_views BIGINT NOT NULL DEFAULT 0;
ALTER TABLE T_PRODUCT ADD COLUMN total_shares BIGINT NOT NULL DEFAULT 0;
ALTER TABLE T_PRODUCT ADD COLUMN total_chats BIGINT NOT NULL DEFAULT 0;
ALTER TABLE T_PRODUCT ADD COLUMN score DECIMAL(20,4) NOT NULL DEFAULT 0;