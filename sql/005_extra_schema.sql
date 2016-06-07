USE hybris;

CREATE TABLE IF NOT EXISTS he_number_space (
  space_name_id VARCHAR(50) NOT NULL,
  seed BIGINT NOT NULL,
  batch_size BIGINT NOT NULL,
  CONSTRAINT pk_space_name_id PRIMARY KEY (space_name_id)
);

INSERT INTO he_number_space (space_name_id, seed, batch_size) VALUES ('hybOrder', 1, 10);