CREATE TABLE hcm_price_info (
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
  qty_adjusted INTEGER NULL,
  sequence        INTEGER      NOT NULL,
  price_info CONSTRAINT dcspp_price_amount_fk REFERENCES dcspp_amount_info(amount_info_id),
  CONSTRAINT hcm_price_adjust_pk PRIMARY KEY (id)
);

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

CREATE TABLE hcm_shipping_group (
  id              BIGINT UNSIGNED,
  order_id        BIGINT UNSIGNED NOT NULL,
  version         INTEGER DEFAULT 0,
  shipping_method INTEGER         NULL,
  shipping_type   INTEGER         NULL,
  ship_on_date    DATE            NULL,
  state           INTEGER DEFAULT 0,
  price_info CONSTRAINT dcspp_ship_grp_amount_fk REFERENCES dcspp_amount_info(amount_info_id),
  CONSTRAINT hcm_ship_group_pk PRIMARY KEY (id),
  CONSTRAINT fk_ship_group_order FOREIGN KEY (order_id) REFERENCES hcm_order (id)
);

CREATE TABLE IF NOT EXISTS hp_address (
  id           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
  profile_id   BIGINT UNSIGNED                NULL,
  address_name VARCHAR(50)                    NOT NULL,
  display_name VARCHAR(50)                    NULL,
  city         VARCHAR(50)                    NOT NULL,
  postal_code  VARCHAR(15)                    NOT NULL,
  country_code VARCHAR(2)                     NOT NULL,
  address      VARCHAR(80)                    NOT NULL,
  state        INTEGER                        NOT NULL,
  CONSTRAINT pk_address_id PRIMARY KEY (id),
  CONSTRAINT hp_address_profile FOREIGN KEY (profile_id) REFERENCES hp_profile (id)
);

CREATE TABLE hcm_pay_group (
  id             BIGINT UNSIGNED,
  order_id       BIGINT UNSIGNED NOT NULL,
  version        INTEGER DEFAULT 0,
  payment_method INTEGER         NULL,
  amount NUMBER (19, 7) NULL,
  amount_authorized NUMBER (19, 7) NULL,
  amount_debited NUMBER (19, 7) NULL,
  amount_credited NUMBER (19, 7) NULL,
  currency_code  VARCHAR(3)      NOT NULL,
  state          INTEGER DEFAULT 0,
  submitted_date DATETIME       NULL,
  CONSTRAINT hcm_pay_group_pk PRIMARY KEY (id)
);

/*create table dcspp_state_history (
    state_history_id number(8,0),
    state_change_date timestamp null,
    state_description varchar(40),
constraint dcspp_state_history_pk primary key(state_history_id));*/

CREATE TABLE hcm_item (
  id      BIGINT UNSIGNED,
  version INTEGER DEFAULT 0,
  #catalog_ref_id CONSTRAINT dcspp_item_cat_fk REFERENCES dcs_catalog (catalog_id
) NOT NULL,
  catalog_ref_id CONSTRAINT dcspp_item_prod_fk REFERENCES hc_product (id
) NOT NULL,
  quantity NUMBER (19, 0
) NULL,
  creation_date TIMESTAMP NULL,
  price_info CONSTRAINT dcspp_item_amount_fk REFERENCES dcspp_amount_info (amount_info_id
),
  order_id CONSTRAINT dcspp_item_order_fk REFERENCES dcspp_order (order_id
) ON DELETE CASCADE NOT NULL,
CONSTRAINT hcm_item_pk PRIMARY KEY (id)
);