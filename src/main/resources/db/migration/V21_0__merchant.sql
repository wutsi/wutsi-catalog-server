CREATE TABLE T_MERCHANT(
    id              SERIAL NOT NULL,

    tenant_id       BIGINT NOT NULL,
    account_id      BIGINT NOT NULL,
    city_id         BIGINT,

    is_enabled      BOOLEAN NOT NULL DEFAULT true,
    product_count   INT NOT NULL DEFAULT 0,
    published_product_count   INT NOT NULL DEFAULT 0,

    UNIQUE (account_id),
    PRIMARY KEY (id)
);

INSERT INTO T_MERCHANT(account_id, is_enabled)
    SELECT DISTINCT account_id, true FROM T_PRODUCT;

UPDATE T_MERCHANT M
SET
    product_count = (
       SELECT count(*)
        FROM T_PRODUCT
        WHERE
            is_deleted=false AND
            account_id=M.account_id
    ),
    published_product_count = (
       SELECT count(*)
        FROM T_PRODUCT
        WHERE
            status=1 AND
            is_deleted=false AND
            account_id=M.account_id
    );

