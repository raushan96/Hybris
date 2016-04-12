USE hybris;

CREATE TABLE IF NOT EXISTS hc_catalog (
  id            VARCHAR(20),
  display_name  VARCHAR(50)                        NULL,
  creation_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  CONSTRAINT hc_catalog_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS hc_category (
  id               VARCHAR(20),
  catalog_id       VARCHAR(20) NOT NULL,
  display_name     VARCHAR(50) NULL,
  long_description TEXT        NULL,
  parent_cat_id    VARCHAR(20) NULL,
  root_category    BOOLEAN DEFAULT 0,
  CONSTRAINT hc_category_pk PRIMARY KEY (id),
  CONSTRAINT hc_category_catalog_fk FOREIGN KEY (catalog_id) REFERENCES hc_catalog (id),
  CONSTRAINT hc_category_pcat_fk FOREIGN KEY (parent_cat_id) REFERENCES hc_category (id)
);

# height, weight, gender, producer, code, catalogs, enabled, catalogs
CREATE TABLE IF NOT EXISTS hc_product (
  id              VARCHAR(20),
  display_name    VARCHAR(50)  NULL,
  description     VARCHAR(254) NULL,
  image           VARCHAR(120) NULL,
  product_type    INTEGER      NULL,
  discountable    BOOLEAN DEFAULT 1,
  start_date      DATETIME     NULL,
  expiration_date DATETIME     NULL,
  brand           VARCHAR(80)  NULL,
  CONSTRAINT hc_product_pk PRIMARY KEY (id)
);

CREATE INDEX hc_prd_type_idx ON hc_product (product_type);

CREATE TABLE IF NOT EXISTS hc_category_products (
  category_id VARCHAR(20),
  product_id  VARCHAR(20),
  CONSTRAINT hc_cat_prod_pk PRIMARY KEY (category_id, product_id),
  CONSTRAINT hc_cat_prod_fk FOREIGN KEY (category_id) REFERENCES hc_category (id),
  CONSTRAINT hc_prod_cat_fk FOREIGN KEY (product_id) REFERENCES hc_product (id)
);

CREATE TABLE IF NOT EXISTS hc_price_list (
  id              VARCHAR(20),
  base_price_list VARCHAR(20),
  description     VARCHAR(80)                        NULL,
  creation_date   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  locale          VARCHAR(5)                         NOT NULL,
  currency        VARCHAR(3)                         NOT NULL,
  CONSTRAINT hc_price_list_pk PRIMARY KEY (id),
  CONSTRAINT hc_price_list_base_fk FOREIGN KEY (base_price_list) REFERENCES hc_price_list (id)
);

CREATE TABLE IF NOT EXISTS hc_price (
  id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
  price_list_id VARCHAR(20) NOT NULL,
  list_price DECIMAL(15 , 3 ) UNSIGNED NOT NULL,
  product_id VARCHAR(20),
  CONSTRAINT hc_price_pk PRIMARY KEY (id),
  CONSTRAINT hc_price_list_fk FOREIGN KEY (price_list_id) REFERENCES hc_price_list (id),
  CONSTRAINT hc_price_prod_fk FOREIGN KEY (product_id) REFERENCES hc_product (id)
);

# CREATE TABLE IF NOT EXISTS dcs_claimable (
#   claimable_id    BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
#   type            INTEGER                        NOT NULL,
#   status          INTEGER                        NULL,
#   start_date      TIMESTAMP                      NULL,
#   expiration_date TIMESTAMP                      NULL,
#   CONSTRAINT dcs_claimable_pk PRIMARY KEY (claimable_id)
# );