USE hybris;

#price lists and catalogs
CREATE TABLE IF NOT EXISTS hs_site (
  id           VARCHAR(100)                       NOT NULL,
  catalog_id   VARCHAR(20)                        NULL,
  display_name VARCHAR(50)                        NOT NULL,
  locale       VARCHAR(5)                         NOT NULL,
  enabled      BOOLEAN DEFAULT 1,
  created      DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  CONSTRAINT hs_site_pk PRIMARY KEY (id),
  CONSTRAINT hs_site_cat_fk FOREIGN KEY (catalog_id) REFERENCES hc_catalog (id)
);

CREATE TABLE IF NOT EXISTS hs_site_urls (
  id  VARCHAR(100) NOT NULL,
  url VARCHAR(100) NOT NULL,
  CONSTRAINT hs_site_url_pk PRIMARY KEY (id, url),
  CONSTRAINT hs_site_url_fk FOREIGN KEY (id) REFERENCES hs_site (id)
);

CREATE TABLE IF NOT EXISTS hs_site_attributes (
  site_id    VARCHAR(100) NOT NULL,
  attr_key   VARCHAR(100) NOT NULL,
  attr_value VARCHAR(100) NOT NULL,
  CONSTRAINT hs_site_attributes_pk PRIMARY KEY (site_id, attr_key),
  CONSTRAINT hs_site_attributes_site_fk FOREIGN KEY (site_id) REFERENCES hs_site (id)
);