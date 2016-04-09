--core
--password - andre 9cf7e1
insert into hp_user(user_id, password, first_name, last_name, gender, email, date_of_birth, accept_emails)
values (1, '$2a$10$zdP5J.iGZkWAhLjuGGQww.l7X.m2datdggOd9mMkzliDj2NY6hbAS', 'andre', 'evans', 0, 'andrey.evans@gmail.com', to_date('03-MAR-1994'), 1);
insert into hp_user(user_id, password, first_name, last_name, gender, email, date_of_birth, accept_emails)
values (2, '$2a$10$zdP5J.iGZkWAhLjuGGQww.l7X.m2datdggOd9mMkzliDj2NY6hbAS', 'andre1', 'evans1', 1, 'andre@gmail.com', to_date('02-MAR-1991'), 0);
insert into hp_user_address(address_id, user_id, company_name, city, postal_code, country_code, address, state)
values(1, 1, 'ExpertSoft', 'Minsk', '123123', 'US', 'korzh 5 d', '2');
insert into hp_user_address(address_id, user_id, company_name, city, postal_code, country_code, address, state)
values(2, 1, 'ExpertSoft', 'Grodno', '123412', 'US', 'malin 6 c', '3');

insert into dps_giftlist(gift_list_id, is_published, creation_date, shipping_addr_id)
values (1, 1, to_date('01-MAR-2018'), 1);
insert into dps_giftitem(gift_item_id, gift_list_id, display_name, description, quantity_desired, quantity_purchased)
values (1, 1, 'gift item 1', 'test description', 1, 0);
insert into dps_giftitem(gift_item_id, gift_list_id, display_name, description, quantity_desired, quantity_purchased)
values (2, 1, 'gift item 2', 'test description new', 3, 0);

insert into hp_user_address(address_id, company_name, city, postal_code, country, address)
values(3, 'ExpertSoft', 'Mensk', '123412', 'Belarus', 'xz 8 h');

commit;

--catalog
insert into dcs_catalog(catalog_id, display_name, creation_date)
values('spices', 'Spice catalog', to_date('03-MAR-2015'));
insert into dcs_category(category_id, catalog_id, display_name, root_category)
values('root-cat', 'spices', 'Root Spice Category', 1);
insert into dcs_category(category_id, catalog_id, display_name, parent_cat_id)
values('a1', 'spices', 'Spice One', 'root-cat');
insert into dcs_category(category_id, catalog_id, display_name, parent_cat_id)
values('a2', 'spices', 'Spice Two', 'root-cat');
insert into dcs_category(category_id, catalog_id, display_name, parent_cat_id)
values('a3', 'spices', 'Spice Three', 'root-cat');
insert into dcs_category(category_id, catalog_id, display_name, parent_cat_id)
values('a4', 'spices', 'Spice Four', 'root-cat');
insert into dcs_category(category_id, catalog_id, display_name, parent_cat_id)
values('a5', 'spices', 'Spice Five', 'root-cat');
insert into dcs_category(category_id, catalog_id, display_name, parent_cat_id)
values('a6', 'spices', 'Spice Six', 'root-cat');
insert into dcs_category(category_id, catalog_id, display_name, parent_cat_id)
values('a7', 'spices', 'Spice Seven', 'root-cat');

insert into dcs_category(category_id, catalog_id, display_name, parent_cat_id)
values('b1', 'spices', 'Spice b1', 'a1');
insert into dcs_category(category_id, catalog_id, display_name, parent_cat_id)
values('b2', 'spices', 'Spice b2', 'a1');
insert into dcs_category(category_id, catalog_id, display_name, parent_cat_id)
values('b3', 'spices', 'Spice b3', 'a1');
insert into dcs_category(category_id, catalog_id, display_name, parent_cat_id)
values('b4', 'spices', 'Spice b4', 'a2');
insert into dcs_category(category_id, catalog_id, display_name, parent_cat_id)
values('b5', 'spices', 'Spice b5', 'a3');

INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (100,'Paloma','facilisis vitae, orci. Phasellus dapibus quam quis diam. Pellentesque senectus ',4,'Elizabeth Lancaster');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (103,'Honorato','parturient montes, nascetur ridiculus mus. Aenean eget magna.Venenatis lacus',5,'Ima Bruce');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (106,'Cally','sit amet, dapibus id, blandit at, nisi. Cum sociis natoque penatibus et magnis',1,'Xyla Bridges');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (109,'Grace','amet, risus. Donec nibh enim, gravida sit amet, dapibus id, blandit at, nisi. Cum sociis',1,'Linda Meyer');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (112,'Linda','odio a purus. Duis elementum, dui quis accumsan convallis, ante lectus convallis est, vita',5,'Mari Saunders');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (115,'Tashya','nostra, per inceptos hymenaeos. Mauris ut quam vel sapien imperdiet ornare. In faucibus.',4,'Urielle Briggs');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (118,'Aristotle','magna. Cras convallis convallis dolor. Quisque idunt vehicula risus. Nulla',2,'April Knox');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (121,'Rashad','neque tellus, imperdiet non, vestibulum nec, euismod in, dolor. Fusce feugiat.',1,'Evelyn Crawford');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (124,'Cassady','neque sed dictum eleifend, nunc risus varius orci, in consequat enim diam vel arcu. ',4,'Hermione Chapman');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (127,'Roth','pede blandit congue. In scelerisque scelerisque dui. Suspendisse ac velit egestas lacinia.',3,'Selma Sharp');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (130,'Katelyn','adipiscing lacus. Ut nec urna et arcu imperdiet ullamcorper. Duis at lacus. Quisque purus ,',6,'Maile Mendoza');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (133,'Arthur','risus. Donec egestas. Duis ac arcu. Nunc mauris. Morbi non sapien molestie orci tincidu.',4,'Eliana Mayo');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (136,'Claudia','tempus mauris erat eget ipsum. Suspendisse sagittis. Nullam vitae diam. Proin dolor. ',4,'Pearl Wade');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (139,'Nola','a, magna. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam laoreet, libero et',1,'Tatum Meadows');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (142,'Zelda','iaculis enim, sit amet ornare lectus justo eu arcu. Morbi sit amet massa. Quisque porttitor',5,'Brynn Rowland');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (145,'Noah','augue ut lacus. Nulla tincidunt, neque vitae semper egestas, urna justo faucibus lectus, a ',1,'Macy Cash');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (148,'Cairo','neque sed sem egestas blandit. Nam nulla magna, malesuada vel, convallis in, cursus et, eros.',1,'Eleanor Robles');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (151,'Nayda','dui, semper et, lacinia vitae, sodales at, velit. Pellentesque ultriciess. Aliquam rutrum lorem',6,'Kevyn Klein');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (154,'Dustin','Quisque libero lacus, varius et, euismod et, commodo at, libero. Morbi accumsan laoreet. ',3,'Wanda Avery');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (157,'Dawn','eget mollis lectus pede et risus. Quisque libero lacus, varius et, euismod et, commodo at,',1,'Althea Evans');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (160,'Cheryl','odio, auctor vitae, aliquet nec, imperdiet nec, leo. Morbi neque tellus, imperdiet no nec,',4,'Kendall Villarreal');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (163,'Francis','Vestibulum ut eros non enim commodo hendrerit. Donec porttitor tellus non magna. Nam ligula,',1,'Florence Mullins');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (166,'Whoopi','elit. Curabitur sed tortor. Integer aliquam adipiscing lacus. Ut nec urna et arcu imperdiet.',3,'Gillian Sargent');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (169,'Xyla','Duis ac arcu. Nunc mauris. Morbi non sapien molestie orci tincidunt adipiscing. Mauris molestie ',4,'Kaden Byrd');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (172,'Guinevere','ut cursus luctus, ipsum leo elementum sem, vitae aliquam eros turpis non enim. Mauris quis',1,'Nina House');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (175,'Roanna','mollis dui, in sodales elit erat vitae risus. Duis a mi fringilla mi lacinia mattis.',1,'Ariana Santiago');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (178,'Yen','sed libero. Proin sed turpis nec mauris blandit mattis. Cras eget nisi dictum augue malesuada',6,'Ocean Aguirre');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (181,'Blaze','nonummy. Fusce fermentum fermentum arcu. Vestibulum ante ipsum primis in faucibus orci  et',2,'Carissa Kirkland');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (184,'Nasim','lacus. Mauris non dui nec urna suscipit nonummy. Fusce fermentum fermentum arcu. Vestibulum an',5,'Sade Burch');
INSERT INTO dcs_product (product_id,display_name,description,product_type,brand) VALUES (187,'Alexandra','tellus. Phasellus elit pede, malesuada vel, venenatis vel. Donec consectetuer mauris id',2,'Hiroko Gonzalez');

insert into dcs_category_products (category_id, product_id)
values('b1', 100);
insert into dcs_category_products (category_id, product_id)
values('b1', 103);
insert into dcs_category_products (category_id, product_id)
values('b1', 106);
insert into dcs_category_products (category_id, product_id)
values('b1', 109);
insert into dcs_category_products (category_id, product_id)
values('b1', 112);

insert into dcs_category_products (category_id, product_id)
values('b2', 115);
insert into dcs_category_products (category_id, product_id)
values('b2', 118);
insert into dcs_category_products (category_id, product_id)
values('b2', 121);
insert into dcs_category_products (category_id, product_id)
values('b2', 124);
insert into dcs_category_products (category_id, product_id)
values('b2', 127);

insert into dcs_category_products (category_id, product_id)
values('b3', 130);
insert into dcs_category_products (category_id, product_id)
values('b3', 133);
insert into dcs_category_products (category_id, product_id)
values('b3', 136);
insert into dcs_category_products (category_id, product_id)
values('b3', 139);
insert into dcs_category_products (category_id, product_id)
values('b3', 142);

insert into dcs_price_list (price_list_id, base_price_list, description, locale, currency)
values('base-list', null, 'Default Price List', 'en_US', 'EUR');
insert into dcs_price_list (price_list_id, base_price_list, description, locale, currency)
values('sales-list', 'base-list', 'Sales Price List', 'en_US', 'EUR');

INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (55,'base-list',41,100);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (56,'base-list',23,103);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (57,'base-list',43,106);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (58,'base-list',47,109);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (59,'base-list',49,112);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (60,'base-list',20,115);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (61,'base-list',47,118);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (62,'base-list',33,121);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (63,'base-list',31,124);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (64,'base-list',40,127);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (65,'base-list',5,130);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (66,'base-list',32,133);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (67,'base-list',27,136);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (68,'base-list',35,139);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (69,'base-list',38,142);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (70,'base-list',25,145);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (71,'base-list',27,148);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (72,'base-list',41,151);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (73,'base-list',49,154);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (74,'base-list',8,157);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (75,'base-list',45,160);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (76,'base-list',6,163);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (77,'base-list',22,166);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (78,'base-list',38,169);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (79,'base-list',19,172);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (80,'base-list',27,175);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (81,'base-list',50,178);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (82,'base-list',32,181);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (83,'base-list',17,184);
INSERT INTO dcs_price (price_id,price_list_id,list_price,product_id) VALUES (84,'base-list',25,187);

insert into dcs_price (price_id, price_list_id, list_price, product_id)
values(1, 'sales-list', 4, 100);
insert into dcs_price (price_id, price_list_id, list_price, product_id)
values(2, 'sales-list', 4, 103);
insert into dcs_price (price_id, price_list_id, list_price, product_id)
values(3, 'sales-list', 3, 106);
insert into dcs_price (price_id, price_list_id, list_price, product_id)
values(4, 'sales-list', 5, 109);
insert into dcs_price (price_id, price_list_id, list_price, product_id)
values(5, 'sales-list', 2, 112);
commit;