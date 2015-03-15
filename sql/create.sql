insert into dps_user(user_id, password, first_name, gender, email, date_of_birth)
            values (1, '$2a$08$b.VA8Yn45uIow6nAFWSU1.cWGPVeu1ZKWhSmit7PVB4xST53EmX.S', 'andre', 0, 'andrey.evans@gmail.com', to_date('03-MAR-1994'));
insert into dps_user_address(address_id, user_id, company_name, city, postal_code, country)
            values(1, 1, 'ExpertSoft', 'Minsk', '123123', 'Belarus');
insert into dps_user_address(address_id, user_id, company_name, city, postal_code, country)
            values(2, 1, 'ExpertSoft', 'Grodno', '123412', 'Belarus');
insert into dps_credit_card(credit_id, credit_card_number, expiration_date, billing_addr)
            values (1, '123123', to_date('01-MAR-2018'), 1);
commit;