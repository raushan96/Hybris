create table dcspp_amount_info (
	amount_info_id	number(8,0),
	type 	integer	null,
	version 	integer	null,
	currency_code 	varchar2(40)	null,
	amount 	number(28, 20)	null,
	discounted 		number(1)	null,
	amount_is_final 	number(1)	null,
	check (discounted in (0, 1)),
	check (amount_is_final in (0, 1)),
	constraint dcspp_amount_info_pk primary key(amount_info_id));

create table dcspp_order (
	order_id	number(8,0),
	user_id constraint dcspp_order_user_fk references dps_user(user_id),
	type	integer	not null,
	version	integer	not null,
	state	varchar2(40)	null,
	creation_date	timestamp	null,
	submitted_date	timestamp	null,
	last_modified_date	timestamp	null,
	price_info	constraint  dcspp_order_amount_fk references dcspp_amount_info(amount_info_id),
	tax_price_info constraint 	dcspp_order_taxAmount_fk references dcspp_amount_info(amount_info_id),
constraint dcspp_order_pk primary key (order_id));

create index order_lastmod_idx on dcspp_order (last_modified_date);
create index order_user_id_idx on dcspp_order (user_id);
create index order_submit_idx on dcspp_order (submitted_date);

create table dcspp_ship_group (
	shipping_group_id	number(8,0),
	type	integer	not null,
	version	integer	not null,
	shipping_method	varchar2(40)	null,
	ship_on_date	timestamp	null,
	state	varchar2(40)	null,
	submitted_date	timestamp	null,
	price_info	varchar2(40)	null,
	order_ref	constraint dcspp_ship_order_fk references dcspp_order(order_id),
constraint dcspp_ship_group_pk primary key (shipping_group_id));

create table dcspp_ship_info (
	shipping_info_id constraint dcspp_ship_info_fk references dcspp_ship_group(shipping_group_id),
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
	payment_group_id	number(8,0)	not null,
	type	integer	not null,
	version	integer	not null,
	payment_method	varchar2(40)	null,
	amount	number(19,7)	null,
	amount_authorized	number(19,7)	null,
	amount_debited	number(19,7)	null,
	amount_credited	number(19,7)	null,
	currency_code	varchar2(10)	null,
	state	varchar2(40)	null,
	submitted_date	timestamp	null,
	order_ref	constraint dcspp_pay_order_fk references dcspp_order(order_id),
constraint dcspp_pay_group_pk primary key (payment_group_id));

create table dcspp_state_history (
	state_history_id number(8,0),
	state_change_date timestamp null,
	state_description varchar2(40),
constraint dcspp_state_history_pk primary key(state_history_id));

create table dcspp_item (
	commerce_item_id	number(8,0),
	type	integer	not null,
	version	integer	not null,
	catalog_ref_id	constraint dcspp_item_cat_fk references dcs_catalog(catalog_id),
	product_id	constraint dcspp_item_prod_fk references dcs_product(product_id),
	quantity	number(19,0)	null,
	state	varchar2(40)	null,
	price_info	varchar2(40)	null,
	order_ref	varchar2(40)	null,
constraint dcspp_item_pk primary key (commerce_item_id));

create index item_catref_idx on dcspp_item (catalog_ref_id);
create index item_prodref_idx on dcspp_item (product_id);

create table dcspp_price_adjust (
	adjustment_id number(8,0),
	version integer	not null,
	adj_description varchar2(254)	null,
	--pricing_model 	varchar2(254)	null references dcs_promotion(promotion_id),
	coupon_id 	references dcs_claimable(claimable_id),
	adjustment 	number(28, 20)	null,
	qty_adjusted 	number(19, 0)	null,
constraint dcspp_price_adjust_pk primary key(adjustment_id));

create table dcspp_amtinfo_adj (
	amount_info_id 	constraint dcspp_amount_fk references dcspp_amount_info(amount_info_id),
	sequence_num 	integer	not null,
	adjustments 	constraint dcspp_adj_fk references dcspp_price_adjust(adjustment_id),
primary key(amount_info_id, sequence_num));

create sequence order_seq start with 100 increment by 10;