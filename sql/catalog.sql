create table dcs_catalog (
	catalog_id	number(8,0),
	display_name	varchar2(254)	null,
	creation_date	timestamp	null
,constraint dcs_catalog_p primary key (catalog_id));

create table dcs_category (
	category_id	number(8,0),
	catalog_id	varchar2(40)	null,
	display_name	varchar2(40)	null,
	description	varchar2(254)	null,
	long_description	clob	null,
	parent_cat_id	varchar2(40) null,
	category_type	integer	null,
	root_category	number(1,0)	null
,constraint dcs_category_pk primary key (category_id)
,constraint dcs_category_root_ch check (root_category in (0,1)));

create index dcs_cat_parent_idx on dcs_category (parent_cat_id);
create index dcs_cat_type_idx on dcs_category (category_type);

create table dcs_product (
	product_id	number(8,0),
	creation_date	timestamp	null,
	display_name	varchar2(40)	null,
	description	varchar2(254)	null,
	parent_cat_id	varchar2(40)	null,
	product_type	integer	null,
	discountable	number(1)	null,
	brand	varchar2(254)	null,
  constraint dcs_product_pk primary key (product_id)
,constraint dcs_product_disc_ch check (discountable in (0,1)));

create index dcs_prd_par_cat_idx on dcs_product (parent_cat_id);
create index dcs_prd_type_idx on dcs_product (product_type);

create table dcs_sku (
	sku_id	number(8,0),
	creation_date	timestamp	null,
	display_name	varchar2(254)	null,
	sku_type	integer	null,
	list_price	number(19,7)	null,
	sale_price	number(19,7)	null,
	on_sale	number(1,0)	null,
	tax_status	integer	null,
	fulfiller	integer	null,
	discountable	number(1)	null
,constraint dcs_sku_pk primary key (sku_id)
,constraint dcs_sku_sale_ch check (on_sale in (0,1)));

create index dcs_sku_lstprice_idx on dcs_sku (list_price);
create index dcs_sku_slprice_idx on dcs_sku (sale_price);
create index dcs_sku_type_idx on dcs_sku (sku_type);