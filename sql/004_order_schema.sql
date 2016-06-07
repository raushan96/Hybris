USE hybris;

/*CREATE TABLE hcm_price_info (
  id              BIGINT UNSIGNED,
  type            INTEGER        NULL,
  version         INTEGER DEFAULT 0,
  amount          DECIMAL(15, 3) NULL,
  raw_amount      DECIMAL(15, 3) NULL,
  tax             DECIMAL(15, 3) NULL,
  shipping        DECIMAL(15, 3) NULL,
  quantity        INTEGER        NULL,
  currency_code   VARCHAR(3)     NOT NULL,
  discounted      BOOL    DEFAULT 0,
  amount_is_final BOOL    DEFAULT 0,
  CONSTRAINT hcm_price_info_pk PRIMARY KEY (id)
);

CREATE TABLE hcm_price_adjust (
  id              BIGINT UNSIGNED,
  version         INTEGER DEFAULT 0,
  adj_description VARCHAR(254) NULL,
  adjustment NUMBER (8, 4) NULL,
  qty_adjusted    INTEGER      NULL,
  sequence        INTEGER      NOT NULL,
  price_info CONSTRAINT dcspp_price_amount_fk REFERENCES dcspp_amount_info(amount_info_id),
  CONSTRAINT hcm_price_adjust_pk PRIMARY KEY (id)
);*/

CREATE TABLE IF NOT EXISTS hcm_order (
  id                 BIGINT UNSIGNED,
  profile_id         BIGINT UNSIGNED NOT NULL,
  number             VARCHAR(50)     NOT NULL,
  site_id            VARCHAR(100)    NOT NULL,
  server_info        VARCHAR(50)     NOT NULL,
  state              INTEGER         NOT NULL,
  version            INTEGER DEFAULT 0,
  creation_date      DATETIME        NOT NULL,
  submitted_date     DATETIME        NULL,
  last_modified_date DATETIME        NOT NULL,
  CONSTRAINT pk_order_id PRIMARY KEY (id),
  CONSTRAINT fk_order_profile FOREIGN KEY (profile_id) REFERENCES hp_profile (id)
);

CREATE INDEX hc_order_number_idx ON hcm_order (number);
CREATE INDEX hc_order_submit_idx ON hcm_order (submitted_date);

# shipping service relation?
CREATE TABLE hcm_hg_shipping_group (
  id           BIGINT UNSIGNED,
  order_id     BIGINT UNSIGNED   NOT NULL,
  version      INTEGER DEFAULT 0,
  index_col    INTEGER UNSIGNED  NULL,
  ship_state   INTEGER DEFAULT 0 NOT NULL,
  city         VARCHAR(50)       NOT NULL,
  postal_code  VARCHAR(15)       NOT NULL,
  country_code VARCHAR(2)        NOT NULL,
  address      VARCHAR(80)       NOT NULL,
  state        INTEGER           NOT NULL,
  CONSTRAINT hcm_ship_group_pk PRIMARY KEY (id),
  CONSTRAINT hcm_shipping_group_order_fk FOREIGN KEY (order_id) REFERENCES hcm_order (id)
);

# authorization status
CREATE TABLE hcm_pay_group (
  id                BIGINT UNSIGNED,
  order_id          BIGINT UNSIGNED  NOT NULL,
  version           INTEGER DEFAULT 0,
  index_col         INTEGER UNSIGNED NULL,
  amount            DECIMAL(15, 3)   NULL,
  amount_authorized DECIMAL(15, 3)   NULL,
  currency_code     VARCHAR(3)       NOT NULL,
  state             INTEGER DEFAULT 0,
  submitted_date    DATETIME         NULL,
  CONSTRAINT hcm_pay_group_pk PRIMARY KEY (id),
  CONSTRAINT hcm_pay_group_order_fk FOREIGN KEY (order_id) REFERENCES hcm_order (id)
);

CREATE TABLE hcm_credit_card (
  id          BIGINT UNSIGNED,
  card_number VARCHAR(50) NULL,
  card_type   VARCHAR(50) NULL,
  owner_title VARCHAR(50) NULL,
  cvv         VARCHAR(3)  NULL,
  exp_date    DATE        NULL,
  CONSTRAINT hcm_credit_card_pk PRIMARY KEY (id),
  CONSTRAINT hcm_credit_card_pg_fk FOREIGN KEY (id) REFERENCES hcm_pay_group (id)
);

# configurable ci, subsku (bundle sku)
CREATE TABLE hcm_item (
  id            BIGINT UNSIGNED,
  order_id      BIGINT UNSIGNED  NOT NULL,
  product_id    VARCHAR(20)      NOT NULL,
  index_col     INTEGER UNSIGNED NULL,
  version       INTEGER DEFAULT 0,
  quantity      INTEGER          NULL,
  creation_date DATETIME         NULL,
  CONSTRAINT hcm_item_pk PRIMARY KEY (id),
  CONSTRAINT hcm_item_order_fk FOREIGN KEY (order_id) REFERENCES hcm_order (id),
  CONSTRAINT hcm_item_prd_fk FOREIGN KEY (product_id) REFERENCES hc_product (id)
);

CREATE TABLE IF NOT EXISTS hcm_identity_generator (
  id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  sequence_name  VARCHAR(100)    NOT NULL,
  sequence_value BIGINT UNSIGNED NOT NULL,
  CONSTRAINT hp_identity_generator_pk PRIMARY KEY (id),
  CONSTRAINT hp_identity_generator_seq_uk UNIQUE (sequence_name)
);