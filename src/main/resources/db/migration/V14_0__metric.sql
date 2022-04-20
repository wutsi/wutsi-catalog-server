CREATE TABLE T_METRIC(
    id              SERIAL NOT NULL,

    period_fk       BIGINT NOT NULL REFERENCES T_PERIOD(id),
    merchant_id     BIGINT NOT NULL,
    product_id      BIGINT NOT NULL,
    type            INT NOT NULL DEFAULT 0,
    value           DECIMAL(20, 4),

    UNIQUE(period_fk, product_id, type),
    PRIMARY KEY (id)
);

CREATE INDEX I_METRIC_merchant_id ON T_METRIC (merchant_id);
CREATE INDEX I_METRIC_product_id ON T_METRIC (product_id);
CREATE INDEX I_METRIC_type ON T_METRIC (type);
