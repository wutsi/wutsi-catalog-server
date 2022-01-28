CREATE TABLE T_PRODUCT(
    id              SERIAL NOT NULL,

    tenant_id       BIGINT NOT NULL,
    account_id      BIGINT NOT NULL,
    category_id     BIGINT,

    title           VARCHAR(100) NOT NULL,
    summary         VARCHAR(160),
    description     TEXT,
    price           DECIMAL(20, 4),
    comparable_price DECIMAL(20, 4),
    currency        VARCHAR(3) NOT NULL,
    is_deleted      BOOL NOT NULL DEFAULT false,
    visible         BOOL NOT NULL DEFAULT true,
    created         TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated         TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted         TIMESTAMPTZ,

    PRIMARY KEY (id)
);

CREATE TABLE T_PICTURE(
    id              SERIAL NOT NULL,
    product_fk      BIGINT NOT NULL REFERENCES T_PRODUCT(id),
    url             TEXT,
    is_deleted      BOOL NOT NULL DEFAULT false,
    created         TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted         TIMESTAMPTZ,

    PRIMARY KEY (id)
);

ALTER TABLE T_PRODUCT ADD COLUMN thumbnail_fk BIGINT REFERENCES T_PICTURE(id);

CREATE OR REPLACE FUNCTION product_updated()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_product_updated
BEFORE UPDATE ON T_PRODUCT
FOR EACH ROW
EXECUTE PROCEDURE product_updated();

