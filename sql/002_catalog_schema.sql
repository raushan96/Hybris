create table dcs_catalog (
    catalog_id    varchar(20),
    display_name    varchar(40)    null,
    creation_date    timestamp    null,
constraint dcs_catalog_p primary key (catalog_id));

create table dcs_category (
    category_id    varchar(20),
    catalog_id    constraint dcs_cata_fk references dcs_catalog(catalog_id) not null,
    display_name    varchar(40)    null,
    long_description    clob    null,
    parent_cat_id    constraint dcs_par_cata_fk references dcs_category(category_id),
    root_category    number(1,0)    default 0,
constraint dcs_category_pk primary key (category_id),
constraint dcs_category_root_ch check (root_category in (0,1)));

create index dcs_cat_parent_idx on dcs_category (parent_cat_id);

create table dcs_product (
    product_id    number(8,0),
    display_name    varchar(40)    null,
    description    varchar(254)    null,
    image    varchar(120) null,
  product_type    integer null,
    discountable    number(1)    default 1,
  start_date    timestamp    null,
  expiration_date    timestamp    null,
    brand    varchar(80)    null,
constraint dcs_product_pk primary key (product_id),
constraint dcs_product_disc_ch check (discountable in (0,1)));

create index dcs_prd_type_idx on dcs_product (product_type);

create table dcs_category_products (
  category_id constraint dcs_cat_prod_fk references dcs_category(category_id),
  product_id constraint dcs_prod_cat_fk references dcs_product(product_id),
constraint dcs_cat_prod_pk primary key(category_id, product_id));

create table dcs_claimable (
  claimable_id    number(8,0),
  type    integer    not null,
  status    integer    null,
  start_date    timestamp    null,
  expiration_date    timestamp    null,
constraint dcs_claimable_pk primary key (claimable_id));

create table dcs_price_list (
  price_list_id    varchar(20),
  base_price_list constraint dcs_price_list_fk references dcs_price_list(price_list_id),
  description    varchar(80) null,
  creation_date    timestamp    null,
  locale    varchar(5)  not null,
  currency    varchar(3)  not null,
constraint dcs_price_list_pk primary key (price_list_id));

create table dcs_price (
  price_id    number(8,0),
  price_list_id constraint dcs_price_fk references dcs_price_list(price_list_id) not null,
  list_price    number(8, 4) not null,
  product_id    constraint dcs_price_prod_fk references dcs_product(product_id) on delete cascade not null,
constraint dcs_price_pk primary key (price_id));

create index dcs_price_prd_idx on dcs_price(product_id);