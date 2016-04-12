USE hybris;

CREATE TABLE IF NOT EXISTS hp_profile (
  id            BIGINT UNSIGNED AUTO_INCREMENT     NOT NULL,
  `password`    VARCHAR(255)                       NOT NULL,
  email         VARCHAR(255)                       NOT NULL,
  first_name    VARCHAR(50)                        NOT NULL,
  last_name     VARCHAR(50)                        NOT NULL,
  gender        INTEGER                            NOT NULL,
  date_of_birth DATE                               NOT NULL,
  accept_emails BOOL DEFAULT 0,
  created       DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  CONSTRAINT pk_profile_id PRIMARY KEY (id),
  CONSTRAINT uq_profile_email UNIQUE (email),
  CONSTRAINT ch_profile_gender CHECK (gender IN (0, 1))
);

CREATE INDEX hp_profile_dob_idx ON hp_profile (date_of_birth);

# separate prop for display name, address name should be only internal var
CREATE TABLE IF NOT EXISTS hp_address (
  id           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
  profile_id   BIGINT UNSIGNED                NULL,
  address_name VARCHAR(50)                    NOT NULL,
  city         VARCHAR(50)                    NOT NULL,
  postal_code  VARCHAR(15)                    NOT NULL,
  country_code VARCHAR(2)                     NOT NULL,
  address      VARCHAR(80)                    NOT NULL,
  state        INTEGER                        NOT NULL,
  CONSTRAINT pk_address_id PRIMARY KEY (id),
  CONSTRAINT hp_address_profile FOREIGN KEY (profile_id) REFERENCES hp_profile (id)
);

CREATE INDEX hp_address_name_idx ON hp_address (address_name);

ALTER TABLE hp_profile ADD shipping_addr_id BIGINT UNSIGNED NOT NULL;
ALTER TABLE hp_profile ADD CONSTRAINT fk_profile_ship_fk FOREIGN KEY (shipping_addr_id) REFERENCES hp_address (id);

CREATE TABLE IF NOT EXISTS hp_interest (
  id           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
  display_name VARCHAR(50)                    NOT NULL,
  code         INTEGER                        NOT NULL,
  CONSTRAINT pk_interest_id PRIMARY KEY (id),
  CONSTRAINT uq_interest_code UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS hp_profile_interests (
  profile_id  BIGINT UNSIGNED NOT NULL,
  interest_id BIGINT UNSIGNED NOT NULL,
  CONSTRAINT hp_prof_interest_pk PRIMARY KEY (profile_id, interest_id),
  CONSTRAINT hp_prof_interest_pr_fk FOREIGN KEY (profile_id) REFERENCES hp_profile (id),
  CONSTRAINT hp_prof_interest_in_fk FOREIGN KEY (interest_id) REFERENCES hp_interest (id)
);

# string comments collection?
CREATE TABLE IF NOT EXISTS hp_wish_list (
  id               BIGINT UNSIGNED                    NOT NULL,
  shipping_addr_id BIGINT UNSIGNED                    NOT NULL,
  is_published     BOOLEAN DEFAULT 0,
  creation_date    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  CONSTRAINT hp_wish_list_pk PRIMARY KEY (id),
  CONSTRAINT hp_wish_list_fk FOREIGN KEY (id) REFERENCES hp_profile (id),
  CONSTRAINT hp_wish_list_ship_fk FOREIGN KEY (shipping_addr_id) REFERENCES hp_address (id)
);

CREATE TABLE IF NOT EXISTS hp_wish_item (
  id               BIGINT UNSIGNED NOT NULL,
  wishlist_id      BIGINT UNSIGNED NOT NULL,
  index_col        INTEGER         NOT NULL,
  #product_id CONSTRAINT gift_item_product_fk REFERENCES dcs_product(product_id),
  display_name     VARCHAR(50)     NOT NULL,
  description      VARCHAR(80)     NULL,
  quantity_desired INTEGER         NOT NULL,
  CONSTRAINT hp_wish_item_pk PRIMARY KEY (id),
  CONSTRAINT hp_wish_item_wish_fk FOREIGN KEY (wishlist_id) REFERENCES hp_wish_list (id)
);