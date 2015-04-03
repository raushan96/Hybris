insert into dps_user(user_id, password, first_name, last_name, gender, email, date_of_birth)
values (1, '$2a$10$zdP5J.iGZkWAhLjuGGQww.l7X.m2datdggOd9mMkzliDj2NY6hbAS', 'andre', 'evans', 0, 'andrey.evans@gmail.com', to_date('03-MAR-1994'));
insert into dps_user_address(address_id, user_id, company_name, city, postal_code, country)
values(1, 1, 'ExpertSoft', 'Minsk', '123123', 'Belarus');
insert into dps_user_address(address_id, user_id, company_name, city, postal_code, country)
values(2, 1, 'ExpertSoft', 'Grodno', '123412', 'Belarus');
insert into dps_credit_card(credit_id, user_id, credit_card_number, expiration_date, billing_addr)
values (1, 1, '123123', to_date('01-MAR-2018'), 1);
insert into dps_giftlist(gift_list_id, is_published, creation_date, shipping_addr_id)
values (1, 1, to_date('01-MAR-2018'), 1);
insert into dps_giftitem(gift_item_id, gift_list_id, display_name, description, quantity_desired, quantity_purchased)
values (1, 1, 'gift item 1', 'test description', 1, 0);
insert into dps_giftitem(gift_item_id, gift_list_id, display_name, description, quantity_desired, quantity_purchased)
values (2, 1, 'gift item 2', 'test description new', 3, 0);
commit;