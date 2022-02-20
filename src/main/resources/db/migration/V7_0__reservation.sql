ALTER TABLE T_PRODUCT ADD CONSTRAINT quantity_min_story CHECK (quantity >= 0);

CREATE TABLE T_RESERVATION(
    id              SERIAL NOT NULL,

    tenant_id       BIGINT NOT NULL,
    order_id        BIGINT NOT NULL,

    status          INT NOT NULL DEFAULT 0,
    created         TIMESTAMPTZ NOT NULL DEFAULT now(),

    PRIMARY KEY (id)
);

CREATE TABLE T_RESERVATION_PRODUCT(
    id              SERIAL NOT NULL,

    reservation_fk  BIGINT NOT NULL REFERENCES T_RESERVATION(id),
    product_fk      BIGINT NOT NULL REFERENCES T_PRODUCT(id),

    quantity        INT NOT NULL,

    UNIQUE(reservation_fk, product_fk),
    PRIMARY KEY (id)
)
