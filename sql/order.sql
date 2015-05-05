create table dcspp_amount_info (
	amount_info_id	number(8,0),
	type 	integer null,
	version 	integer default 0,
	currency_code 	varchar2(3)	null,
	amount 	number(8, 4)	null,
	raw_amount 	number(8, 4)	null,
	tax number(8, 4)	null,
	shipping 	number(8, 4)	null,
	quantity number(4) null,
	discounted 		number(1)	null,
	amount_is_final 	number(1)	null,
check (discounted in (0, 1)),
check (amount_is_final in (0, 1)),
constraint dcspp_amount_info_pk primary key(amount_info_id));

create table dcspp_order (
	order_id	number(8,0),
	order_number	varchar2(20) null,
	coupon_id varchar2(20) null,
	user_id constraint dcspp_order_user_fk references dps_user(user_id) not null,
	version	integer	default 0,
	state	integer	default 0,
	creation_date	timestamp,
	submitted_date	timestamp	null,
	last_modified_date	timestamp,
	order_price_info	constraint  dcspp_order_amount_fk references dcspp_amount_info(amount_info_id) not null,
	tax_price_info constraint 	dcspp_order_taxAmount_fk references dcspp_amount_info(amount_info_id),
constraint dcspp_order_pk primary key (order_id));

create index order_lastmod_idx on dcspp_order (last_modified_date);
create index order_user_id_idx on dcspp_order (user_id);
create index order_submit_idx on dcspp_order (submitted_date);
create index order_price_idx on dcspp_order (order_price_info);
create index order_price_tax_idx on dcspp_order (tax_price_info);

create table dcspp_ship_group (
	shipping_group_id	number(8,0),
	version	integer	default 0,
	shipping_method	integer null,
	shipping_type	integer null,
	ship_on_date	date	null,
	state	integer	default 0,
	price_info	constraint dcspp_ship_grp_amount_fk references dcspp_amount_info(amount_info_id),
	order_id	constraint dcspp_ship_order_fk references dcspp_order(order_id) on delete cascade not null,
constraint dcspp_ship_group_pk primary key (shipping_group_id));

create index ship_group_price_idx on dcspp_ship_group (price_info);
create index ship_group_order_idx on dcspp_ship_group (order_id);

create table dcspp_ship_info (
	shipping_info_id constraint dcspp_ship_info_fk references dcspp_ship_group(shipping_group_id) on delete cascade,
	version integer default 0,
	first_name	varchar2(40)	null,
	last_name	varchar2(40)	null,
	email	varchar2(40)	null,
	city	varchar2(40)	null,
	postal_code	varchar2(15)	null,
	phone_number varchar2(40) null,
	state varchar2(40) null,
	country	varchar2(40)	null,
constraint dcspp_ship_info_pk primary key(shipping_info_id));

create table dcspp_pay_group (
	payment_group_id	number(8,0),
	version	integer	default 0,
	payment_method	integer	null,
	amount	number(19,7)	null,
	amount_authorized	number(19,7)	null,
	amount_debited	number(19,7)	null,
	amount_credited	number(19,7)	null,
	currency_code	varchar2(3)	null,
	state	integer	default 0,
	submitted_date	timestamp	null,
	order_id	constraint dcspp_pay_order_fk references dcspp_order(order_id) on delete cascade not null,
constraint dcspp_pay_group_pk primary key (payment_group_id));

create index pay_group_order_idx on dcspp_pay_group (order_id);

/*create table dcspp_state_history (
	state_history_id number(8,0),
	state_change_date timestamp null,
	state_description varchar2(40),
constraint dcspp_state_history_pk primary key(state_history_id));*/

create table dcspp_item (
	commerce_item_id	number(8,0),
	version	integer	default 0,
	--catalog_ref_id	constraint dcspp_item_cat_fk references dcs_catalog(catalog_id) not null,
	product_id	constraint dcspp_item_prod_fk references dcs_product(product_id) not null,
	quantity	number(19,0)	null,
	creation_date	timestamp null,
	price_info	constraint dcspp_item_amount_fk references dcspp_amount_info(amount_info_id),
	order_id	constraint dcspp_item_order_fk references dcspp_order(order_id) on delete cascade not null,
constraint dcspp_item_pk primary key (commerce_item_id));

-- create index item_catref_idx on dcspp_item (catalog_ref_id);
create index item_prodref_idx on dcspp_item (product_id);
create index item_order_idx on dcspp_item (order_id);
create index item_price_idx on dcspp_item (price_info);

create table dcspp_price_adjust (
	adjustment_id number(8,0),
	version integer	default 0,
	adj_description varchar2(254)	null,
	coupon_id 	references dcs_claimable(claimable_id),
	adjustment 	number(8, 4)	null,
	qty_adjusted 	number(4, 0)	null,
	sequence	integer not null,
	price_info	constraint dcspp_price_amount_fk references dcspp_amount_info(amount_info_id),
constraint dcspp_price_adjust_pk primary key(adjustment_id));

create index price_coupon_idx on dcspp_price_adjust (coupon_id);

create sequence order_seq start with 100 increment by 10;