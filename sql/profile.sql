create table dps_user (
	user_id	number(8,0),
	password	varchar2(120) not null,
	first_name	varchar2(40)	null,
	last_name	varchar2(40)	null,
	gender number(1, 0) not null,
	email	varchar2(40)	null,
	date_of_birth	date	null,
constraint dps_user_pk primary key(user_id),
constraint dps_user_un_email unique (email),
constraint dps_user_ch_gender check(gender in (0, 1)));

create table dps_user_address (
	address_id	number(8,0),
  user_id constraint address_user_fk references dps_user(user_id) on delete cascade,
	company_name	varchar2(40)	null,
	city	varchar2(40)	null,
	postal_code	varchar2(15)	null,
	country	varchar2(40)	null,
constraint dps_user_address_pk primary key(address_id));

create index dps_addr_comp_idx on dps_user_address(company_name);
create index dps_addr_user_idx on dps_user_address(user_id);


create table dps_credit_card (
	credit_id number(8,0),
	user_id	constraint credit_user_fk	references dps_user(user_id) on delete cascade,
	credit_card_number	varchar2(40)	not null,
	expiration_date	date not null,
	billing_addr constraint credit_address_fk references dps_user_address(address_id),
constraint dps_credit_card_pk primary key(credit_id));

create index dps_credit_addr_idx on dps_credit_card(billing_addr);

create table dps_giftlist (
	gift_list_id	number(8, 0) constraint gift_list_user_fk references dps_user(user_id) on delete cascade,
	is_published	number(1,0)	default 0,
	creation_date	timestamp	null,
	shipping_addr_id	constraint gift_list_address_fk references dps_user_address(address_id),
constraint dps_giftlist_pk primary key (gift_list_id),
constraint dps_giftlist_publ_ch check (is_published in (0,1)));

create index gftlst_shpadid_idx on dps_giftlist (shipping_addr_id);

--cascade on product?
create table dps_giftitem (
	gift_item_id	number(8,0),
	product_id	constraint gift_item_product_fk references dcs_product(product_id),
	gift_list_id	constraint gift_item_list_fk references dps_giftlist(gift_list_id) on delete cascade,
	display_name	varchar2(40)	null,
	description	varchar2(80)	null,
	quantity_desired	integer	null,
	quantity_purchased	integer	null,
constraint dps_giftitem_pk primary key (gift_item_id));

create index giftitem_prod_idx on dps_giftitem (product_id);

--needs rework
create table dps_role (
	role_id	number(8,0),
	name	varchar2(40)	not null,
	description	varchar2(254)	null,
	parent_role	constraint role_parent_fk references dps_role (role_id) on delete cascade,
constraint dps_role_pk primary key(role_id));

create index dps_parent_role_idx on dps_role (parent_role);

create table dps_user_role (
	user_id	constraint user_role_fk references dps_user (user_id) on delete cascade,
	hybris_role constraint role_user_fk references dps_role (role_id) on delete cascade,
constraint dps_user_role_pk primary key (user_id, hybris_role));

create index dps_usr_roles_idx on dps_user_role (hybris_role);

create sequence profile_seq start with 100 increment by 1;
create sequence global_seq start with 30 increment by 1;