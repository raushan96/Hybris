# product -> list of products, set of sites, prices
# sku -> parent product

USE hybris;
CREATE TABLE IF NOT EXISTS hc_catalog (
  id            VARCHAR(20),
  display_name  VARCHAR(50)                        NOT NULL,
  code          VARCHAR(50)                        NOT NULL,
  creation_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  CONSTRAINT hc_catalog_pk PRIMARY KEY (id),
  CONSTRAINT uq_catalog_code UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS hc_category (
  id               VARCHAR(20),
  display_name     VARCHAR(50)                        NOT NULL,
  long_description TEXT                               NULL,
  sites            VARCHAR(200)                       NULL,
  parent_cat_id    VARCHAR(20)                        NULL,
  root_category    BOOLEAN DEFAULT 0,
  creation_date    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  CONSTRAINT hc_category_pk PRIMARY KEY (id),
  CONSTRAINT hc_category_parent_cat_fk FOREIGN KEY (parent_cat_id) REFERENCES hc_category (id)
);

CREATE INDEX hc_category_name_idx ON hc_category (display_name);

CREATE TABLE IF NOT EXISTS hc_catalog_categories (
  catalog_id  VARCHAR(20),
  category_id VARCHAR(20),
  CONSTRAINT hc_catalog_cat_pk PRIMARY KEY (catalog_id, category_id),
  CONSTRAINT hc_catalog_cat_ctg_fk FOREIGN KEY (catalog_id) REFERENCES hc_catalog (id),
  CONSTRAINT hc_catalog_cat_ctl_fk FOREIGN KEY (category_id) REFERENCES hc_category (id)
);

# gender, attributes
CREATE TABLE IF NOT EXISTS hc_product (
  id              VARCHAR(20),
  display_name    VARCHAR(50)  NOT NULL,
  product_code    VARCHAR(50)  NOT NULL,
  description     VARCHAR(255) NULL,
  image           VARCHAR(120) NULL,
  discountable    BOOLEAN DEFAULT 1,
  enabled         BOOLEAN DEFAULT 1,
  start_date      DATETIME     NULL,
  expiration_date DATETIME     NULL,
  brand           VARCHAR(80)  NULL,
  CONSTRAINT hc_product_pk PRIMARY KEY (id),
  CONSTRAINT uq_hc_product_code UNIQUE (product_code)
);

#sku table - size, color, site, code, personalizable, visible, origin

CREATE INDEX hc_prd_name_idx ON hc_product (display_name);

CREATE TABLE IF NOT EXISTS hc_category_products (
  category_id VARCHAR(20),
  product_id  VARCHAR(20),
  CONSTRAINT hc_cat_prod_pk PRIMARY KEY (category_id, product_id),
  CONSTRAINT hc_cat_prod_fk FOREIGN KEY (category_id) REFERENCES hc_category (id),
  CONSTRAINT hc_prod_cat_fk FOREIGN KEY (product_id) REFERENCES hc_product (id)
);

CREATE TABLE IF NOT EXISTS hc_price_list (
  id              VARCHAR(20),
  base_price_list VARCHAR(20)                        NULL,
  description     VARCHAR(80)                        NULL,
  creation_date   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  locale          VARCHAR(5)                         NOT NULL,
  currency        VARCHAR(3)                         NOT NULL,
  CONSTRAINT hc_price_list_pk PRIMARY KEY (id),
  CONSTRAINT hc_price_list_base_fk FOREIGN KEY (base_price_list) REFERENCES hc_price_list (id)
);

CREATE TABLE IF NOT EXISTS hc_price (
  id            VARCHAR(20) ,
  price_list_id VARCHAR(20)             NOT NULL,
  list_price    DECIMAL(15, 3) UNSIGNED NOT NULL,
  product_id    VARCHAR(20),
  CONSTRAINT hc_price_pk PRIMARY KEY (id),
  CONSTRAINT hc_price_list_fk FOREIGN KEY (price_list_id) REFERENCES hc_price_list (id),
  CONSTRAINT hc_price_prod_fk FOREIGN KEY (product_id) REFERENCES hc_product (id)
);