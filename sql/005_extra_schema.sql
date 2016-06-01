USE hybris;

CREATE TABLE IF NOT EXISTS identity_generator (
  id             BIGINT UNSIGNED AUTO_INCREMENT,
  sequence_name  VARCHAR(100)    NOT NULL,
  sequence_value BIGINT UNSIGNED NOT NULL,
  CONSTRAINT identity_generator_pk PRIMARY KEY (id)
);