DROP TABLE T_MERCHANT;

CREATE TABLE T_MERCHANT(
    id              SERIAL NOT NULL,

    tenant_id       BIGINT NOT NULL,
    account_id      BIGINT NOT NULL,
    city_id         BIGINT,

    is_enabled      BOOLEAN NOT NULL DEFAULT true,

    UNIQUE (account_id),
    PRIMARY KEY (id)
);

INSERT INTO T_MERCHANT(account_id, is_enabled, tenant_id)
    SELECT DISTINCT account_id, true, tenant_id FROM T_PRODUCT;
