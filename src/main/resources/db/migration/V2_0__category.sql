CREATE TABLE T_CATEGORY(
    id              SERIAL NOT NULL,

    tenant_id       BIGINT NOT NULL,
    account_id      BIGINT NOT NULL,

    title           VARCHAR(100) NOT NULL,
    sort_order      INT NOT NULL DEFAULT 0,
    is_deleted      BOOL NOT NULL DEFAULT false,
    created         TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated         TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted         TIMESTAMPTZ,

    PRIMARY KEY (id)
);

CREATE TABLE T_PRODUCT_CATEGORY(
    product_fk      BIGINT NOT NULL REFERENCES T_PRODUCT(id),
    category_fk     BIGINT NOT NULL REFERENCES T_CATEGORY(id),

    PRIMARY KEY (product_fk, category_fk)
);

CREATE OR REPLACE FUNCTION category_updated()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_category_updated
BEFORE UPDATE ON T_CATEGORY
FOR EACH ROW
EXECUTE PROCEDURE category_updated();

