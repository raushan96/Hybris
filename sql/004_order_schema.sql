USE hybris;

CREATE TABLE hcm_price_info (
  id                  BIGINT UNSIGNED,
  dtype               VARCHAR(30)    NOT NULL,
  version             INTEGER DEFAULT 0,
  amount              DECIMAL(15, 3) NOT NULL,
  raw_amount          DECIMAL(15, 3) NOT NULL,
  shipping            DECIMAL(15, 3) NULL,
  tax                 DECIMAL(15, 3) NULL,
  quantity_discounted INTEGER        NULL,
  currency       VARCHAR(3)     NOT NULL,
  discounted          BOOL    DEFAULT 0,
  amount_is_final     BOOL    DEFAULT 0,
  CONSTRAINT pk_price_info_id PRIMARY KEY (id)
);


CREATE TABLE hcm_price_adjust (
  id              BIGINT UNSIGNED,
  price_info_id   BIGINT UNSIGNED  NOT NULL,
  version         INTEGER DEFAULT 0,
  index_col       INTEGER UNSIGNED NULL,
  adj_description VARCHAR(254)     NOT NULL,
  adjustment      DECIMAL(15, 3)   NOT NULL,
  qty_adjusted    INTEGER          NOT NULL,
  CONSTRAINT pk_price_adjust_id PRIMARY KEY (id),
  CONSTRAINT fk_price_adj_info FOREIGN KEY (price_info_id) REFERENCES hcm_price_info (id)
);

CREATE TABLE IF NOT EXISTS hcm_order (
  id                 BIGINT UNSIGNED,
  profile_id         BIGINT UNSIGNED NOT NULL,
  price_info_id      BIGINT UNSIGNED NOT NULL,
  number             VARCHAR(50)     NOT NULL,
  site_id            VARCHAR(100)    NOT NULL,
  server_info        VARCHAR(50)     NOT NULL,
  state              INTEGER         NOT NULL,
  version            INTEGER DEFAULT 0,
  creation_date      DATETIME        NOT NULL,
  submitted_date     DATETIME        NULL,
  last_modified_date DATETIME        NOT NULL,
  CONSTRAINT pk_order_id PRIMARY KEY (id),
  CONSTRAINT fk_order_profile FOREIGN KEY (profile_id) REFERENCES hp_profile (id),
  CONSTRAINT fk_order_price_info FOREIGN KEY (price_info_id) REFERENCES hcm_price_info (id)
);

CREATE INDEX hc_order_number_idx ON hcm_order (number);
CREATE INDEX hc_order_submit_idx ON hcm_order (submitted_date);

# shipping service relation?
CREATE TABLE hcm_hg_shipping_group (
  id            BIGINT UNSIGNED,
  order_id      BIGINT UNSIGNED   NOT NULL,
  price_info_id BIGINT UNSIGNED   NOT NULL,
  version       INTEGER DEFAULT 0,
  index_col     INTEGER UNSIGNED  NULL,
  ship_state    INTEGER DEFAULT 0 NOT NULL,
  city          VARCHAR(50)       NOT NULL,
  postal_code   VARCHAR(15)       NOT NULL,
  country_code  VARCHAR(2)        NOT NULL,
  address       VARCHAR(80)       NOT NULL,
  state         INTEGER           NOT NULL,
  CONSTRAINT pk_ship_group_id PRIMARY KEY (id),
  CONSTRAINT fk_shipping_group_order_fk FOREIGN KEY (order_id) REFERENCES hcm_order (id),
  CONSTRAINT fk_shipping_price_info FOREIGN KEY (price_info_id) REFERENCES hcm_price_info (id)
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
  CONSTRAINT pk_pay_group_id PRIMARY KEY (id),
  CONSTRAINT fk_pay_group_order FOREIGN KEY (order_id) REFERENCES hcm_order (id)
);

CREATE TABLE hcm_credit_card (
  id          BIGINT UNSIGNED,
  card_number VARCHAR(50) NULL,
  card_type   VARCHAR(50) NULL,
  owner_title VARCHAR(50) NULL,
  cvv         VARCHAR(3)  NULL,
  exp_date    DATE        NULL,
  CONSTRAINT pk_credit_card_id PRIMARY KEY (id),
  CONSTRAINT fk_credit_card_pg FOREIGN KEY (id) REFERENCES hcm_pay_group (id)
);

# configurable ci, subsku (bundle sku)
CREATE TABLE hcm_item (
  id            BIGINT UNSIGNED,
  order_id      BIGINT UNSIGNED  NOT NULL,
  product_id    VARCHAR(20)      NOT NULL,
  price_info_id BIGINT UNSIGNED  NOT NULL,
  index_col     INTEGER UNSIGNED NULL,
  version       INTEGER DEFAULT 0,
  quantity      INTEGER          NULL,
  creation_date DATETIME         NULL,
  CONSTRAINT pk_item_id PRIMARY KEY (id),
  CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES hcm_order (id),
  CONSTRAINT fk_item_prd FOREIGN KEY (product_id) REFERENCES hc_product (id),
  CONSTRAINT fk_item_price_info FOREIGN KEY (price_info_id) REFERENCES hcm_price_info (id)
);

CREATE TABLE hcm_ship_item (
  id            BIGINT UNSIGNED,
  ship_group_id BIGINT UNSIGNED NOT NULL,
  item_id       BIGINT UNSIGNED NOT NULL,
  version       INTEGER DEFAULT 0,
  type          INTEGER         NOT NULL,
  quantity      INTEGER         NULL,
  CONSTRAINT pk_item_id PRIMARY KEY (id),
  CONSTRAINT fk_sh_item FOREIGN KEY (item_id) REFERENCES hcm_item (id),
  CONSTRAINT fk_it_ship FOREIGN KEY (ship_group_id) REFERENCES hcm_hg_shipping_group (id)
);

CREATE TABLE IF NOT EXISTS hcm_identity_generator (
  id             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  sequence_name  VARCHAR(100)    NOT NULL,
  sequence_value BIGINT UNSIGNED NOT NULL,
  CONSTRAINT hp_identity_generator_pk PRIMARY KEY (id),
  CONSTRAINT hp_identity_generator_seq_uk UNIQUE (sequence_name)
);