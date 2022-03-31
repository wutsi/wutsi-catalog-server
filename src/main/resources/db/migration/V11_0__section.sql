CREATE TABLE T_SECTION(
    id              SERIAL NOT NULL,

    tenant_id       BIGINT NOT NULL,
    account_id      BIGINT NOT NULL,

    title           VARCHAR(100) NOT NULL,
    sort_order      INT NOT NULL DEFAULT 0,
    product_count   INT NOT NULL DEFAULT 0,
    is_deleted      BOOL NOT NULL DEFAULT false,
    created         TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated         TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted         TIMESTAMPTZ,

    PRIMARY KEY (id)
);

CREATE TABLE T_SECTION_PRODUCT(
    section_fk   BIGINT NOT NULL REFERENCES T_SECTION(id),
    product_fk   BIGINT NOT NULL REFERENCES T_PRODUCT(id),

    PRIMARY KEY (section_fk, product_fk)
);
