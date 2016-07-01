use hybris;

# profile
insert into hp_profile(id, password, first_name, last_name, gender, email, date_of_birth, accept_emails)
values (1, '$2a$10$zdP5J.iGZkWAhLjuGGQww.l7X.m2datdggOd9mMkzliDj2NY6hbAS', 'andre', 'evans', 0, 'andrey.evans@gmail.com', to_date('03-MAR-1994'), 1);
insert into hp_profile(id, password, first_name, last_name, gender, email, date_of_birth, accept_emails)
values (2, '$2a$10$zdP5J.iGZkWAhLjuGGQww.l7X.m2datdggOd9mMkzliDj2NY6hbAS', 'andre1', 'evans1', 1, 'andre@gmail.com', to_date('02-MAR-1991'), 0);
insert into hp_address(id, profile_id, city, postal_code, country_code, address, state)
values(1, 1, 'Minsk', '123123', 'US', 'korzh 5 d', '2');
insert into hp_address(id, profile_id, city, postal_code, country_code, address, state)
values(2, 1, 'Grodno', '123412', 'US', 'malin 6 c', '3');

insert into hp_wish_list(id, is_published, shipping_addr_id)
values (1, 1, 1);

insert into hp_address(id, city, postal_code, country_code, address)
values(3, 'Mensk', '123412', 'BY', 'xz 8 h');

commit;

# catalog
INSERT INTO `hybris`.`hc_catalog` (`id`, `display_name`, `code`) VALUES ('spices', 'Spice Catalog', 'hyb_cat');
insert into hc_category(id, display_name, sites, root_category)
values('root-cat', 'Root Spice Category', 'hybris-site, natura-site, chile-site', 1);
INSERT INTO `hybris`.`hc_catalog_categories` (`catalog_id`, `category_id`) VALUES ('spices', 'root-cat');


insert into hc_category(id, display_name, parent_cat_id)
values('a1', 'Spice One', 'root-cat');
insert into hc_category(id, display_name, parent_cat_id)
values('a2', 'Spice Two', 'root-cat');
insert into hc_category(id, display_name, parent_cat_id)
values('a3', 'Spice Three', 'root-cat');
insert into hc_category(id, display_name, parent_cat_id)
values('a4', 'Spice Four', 'root-cat');
insert into hc_category(id, display_name, parent_cat_id)
values('a5', 'Spice Five', 'root-cat');
insert into hc_category(id, display_name, parent_cat_id)
values('a6', 'Spice Six', 'root-cat');
insert into hc_category(id, display_name, parent_cat_id)
values('a7', 'Spice Seven', 'root-cat');

insert into hc_category(id, display_name, parent_cat_id)
values('b1', 'Spice b1', 'a1');
insert into hc_category(id, display_name, parent_cat_id)
values('b2', 'Spice b2', 'a1');
insert into hc_category(id, display_name, parent_cat_id)
values('b3', 'Spice b3', 'a1');
insert into hc_category(id, display_name, parent_cat_id)
values('b4', 'Spice b4', 'a2');
insert into hc_category(id, display_name, parent_cat_id)
values('b5', 'Spice b5', 'a3');

commit;

INSERT INTO `hc_product` (`id`,`display_name`,`product_code`,`description`,`brand`) VALUES (300,"Penicillin VK","C896C5F7-E1A4-9C96-E0E0-02C13EE4AE5B","ante, iaculis nec, eleifend non, dapibus","Cheratussin AC"),(301,"Actos","997CA9B0-14E6-FAC8-7F58-BBFFF0D5710B","mauris ut mi. Duis risus odio, auctor vitae, aliquet nec,","Cyclobenzaprin HCl"),(302,"Lipitor","7EB8C140-08B4-53FB-19A3-4F3C7987D939","facilisis eget, ipsum. Donec sollicitudin adipiscing ligula.","Meloxicam"),(303,"Doxycycline Hyclate","91473E66-F59F-748A-FB74-8B77434F57E5","amet luctus vulputate, nisi sem semper erat, in consectetuer ipsum","Albuterol"),(304,"Amoxicillin","A468C47F-B2FD-FEDA-DB38-AA9E5D7B8435","orci luctus et ultrices posuere cubilia Curae;","Benicar HCT"),(305,"Cyclobenzaprin HCl","CEBAC17A-7404-390F-436C-BCB69D2F65FB","Etiam imperdiet dictum magna. Ut tincidunt orci quis","Paroxetine HCl"),(306,"Nuvaring","08C1C9B0-73E2-87B2-3A57-C3FC08B56D26","facilisis. Suspendisse commodo tincidunt nibh. Phasellus nulla. Integer vulputate,","Hydrocodone/APAP"),(307,"Pantoprazole Sodium","2171418F-AAB9-2AB7-8AE6-DC39AC32BCDF","risus. In mi pede, nonummy ut, molestie in, tempus eu,","Penicillin VK"),(308,"Nuvaring","C0D3AA8B-ECAA-3AF7-0EF1-8A0027AB6ECD","in aliquet lobortis, nisi nibh lacinia orci,","Omeprazole (Rx)"),(309,"Premarin","F5F2ADB9-8F2E-74AA-3C46-2696C7759976","risus odio, auctor vitae, aliquet nec, imperdiet nec,","Metformin HCl"),(310,"Seroquel","3DDC453D-B6DE-F417-2BC0-AB6EB9000131","pede, ultrices a, auctor non, feugiat","Sertraline HCl"),(311,"Nexium","2FD9240A-6D0C-61ED-5D0B-F031DEA059D0","quam. Curabitur vel lectus. Cum sociis","Lyrica"),(312,"Alendronate Sodium","AF155761-1275-29AD-B93B-A53177D5FBC4","aliquet diam. Sed diam lorem, auctor","Hydrocodone/APAP"),(313,"Gabapentin","A103FD6E-CA90-526A-8C27-B2DEA78B8B0A","et, commodo at, libero. Morbi accumsan laoreet ipsum. Curabitur consequat,","Simvastatin"),(314,"Hydrochlorothiazide","CF98B6DC-2180-53E0-75A2-F6AF850FB1DE","Aenean eget metus. In nec orci. Donec","Spiriva Handihaler"),(315,"Carisoprodol","A7C286D8-A26A-0886-D25A-3CB013DE5472","eros. Nam consequat dolor vitae dolor. Donec fringilla.","TriNessa"),(316,"Clindamycin HCl","C581BEDC-7D74-F2EF-6835-DE2971C7A149","porttitor eros nec tellus. Nunc lectus","Amoxicillin"),(317,"Fluticasone Propionate","D9D87897-27FD-C6EB-4BD0-FE29FAD73FB0","mollis lectus pede et risus. Quisque libero","Nasonex"),(318,"Lorazepam","75A318D2-8EDC-E562-EDC4-D018BBB305E0","consectetuer mauris id sapien. Cras dolor dolor, tempus","Endocet"),(319,"Amlodipine Besylate","20DF40F7-D92A-7E69-A44D-FBC01AE4CEE4","felis. Nulla tempor augue ac ipsum. Phasellus vitae mauris sit","Lexapro"),(320,"Lidoderm","9EAFF9A5-C35B-410E-9701-393CB7863E61","et risus. Quisque libero lacus, varius et, euismod et, commodo","Furosemide"),(321,"Singulair","70807AAF-2A1F-85B0-E3A4-DA7A7161AA4C","nibh enim, gravida sit amet, dapibus","Furosemide"),(322,"Celebrex","EDE1233A-04A6-86B5-2C18-6ED858B7CC6D","ac sem ut dolor dapibus gravida. Aliquam tincidunt, nunc ac","Simvastatin"),(323,"Atenolol","6688A96E-2D6B-620F-637B-A3FBD6F198A3","iaculis aliquet diam. Sed diam lorem, auctor quis,","Lisinopril"),(324,"Losartan Potassium","EFB8E840-BC0C-67DB-F435-B7FE3BBF0BD4","molestie orci tincidunt adipiscing. Mauris molestie pharetra nibh. Aliquam ornare,","Omeprazole (Rx)"),(325,"Promethazine HCl","2BC0A345-FC0F-8695-CE05-D7A920A8C783","pede et risus. Quisque libero lacus,","Nasonex"),(326,"Pravastatin Sodium","29A21BAC-806F-B3A9-91AD-53F43074573A","mauris. Integer sem elit, pharetra ut, pharetra","Azithromycin"),(327,"Diovan","4BE6D026-5FF4-53D2-0B49-BCEF97036061","risus, at fringilla purus mauris a","Prednisone"),(328,"Tri-Sprintec","5DE037CD-4692-37CD-4321-34CF90E17A7E","nisi. Mauris nulla. Integer urna. Vivamus","Omeprazole (Rx)"),(329,"Azithromycin","B2C7E680-94C2-C3DF-31CD-A3DC8BDDB74B","sodales nisi magna sed dui. Fusce aliquam,","Famotidine");
INSERT INTO `hc_product` (`id`,`display_name`,`product_code`,`description`,`brand`) VALUES (330,"Advair Diskus","F200E701-2C3D-C8EB-A80F-1E4D01C4F664","eleifend non, dapibus rutrum, justo. Praesent luctus. Curabitur egestas","Lisinopril"),(331,"Diovan","14E4A423-F21A-4B28-2A56-06942D6F328D","tristique senectus et netus et malesuada fames","Ranitidine HCl"),(332,"Amoxicillin Trihydrate/Potassium Clavulanate","6D26C976-868C-99D8-4851-8DD78A5B92A6","dolor. Donec fringilla. Donec feugiat metus sit amet","Amlodipine Besylate"),(333,"Amlodipine Besylate","12F89C5F-1B1F-6AE1-679A-A9D7A37FA4F8","Suspendisse commodo tincidunt nibh. Phasellus nulla. Integer vulputate, risus","Amoxicillin"),(334,"Spiriva Handihaler","1CF2F695-672D-C24D-70C1-FD879CFDC859","amet, consectetuer adipiscing elit. Aliquam auctor, velit","Gabapentin"),(335,"Diovan","346ED1A9-B2CF-47D5-82D8-B02079B69E27","eu turpis. Nulla aliquet. Proin velit. Sed malesuada augue ut","Doxycycline Hyclate"),(336,"Lisinopril","FA52109E-3933-8471-47CD-8000DD948C23","Proin sed turpis nec mauris blandit mattis.","Warfarin Sodium"),(337,"Digoxin","A75EB89A-D965-00DF-8602-6472C5BB2D23","convallis ligula. Donec luctus aliquet odio. Etiam ligula tortor, dictum","Amoxicillin"),(338,"Tamsulosin HCl","04AB1E12-6A52-E742-10D5-6908F2BC5FF1","aptent taciti sociosqu ad litora torquent","Clonazepam"),(339,"Hydrocodone/APAP","55401648-2241-63F2-ACD1-14703F5ACB7F","nisi. Cum sociis natoque penatibus et magnis","Vitamin D (Rx)"),(340,"Oxycodone HCl","A364BD70-A9A9-4BB9-0E25-801D75CD5B57","dolor dolor, tempus non, lacinia at, iaculis quis,","Amoxicillin Trihydrate/Potassium Clavulanate"),(341,"Sertraline HCl","1CFEF327-4099-895F-97C8-4F9FD0876580","at, velit. Cras lorem lorem, luctus ut, pellentesque eget,","Lisinopril"),(342,"Cymbalta","42276AE4-6609-A015-3C46-AF2FD4542265","ac turpis egestas. Fusce aliquet magna a neque. Nullam","Crestor"),(343,"Diovan","3F6BA381-B495-9519-6A1F-50F27303DC82","sit amet ultricies sem magna nec quam. Curabitur vel","Oxycontin"),(344,"Albuterol","97BF9B25-2766-8DE8-A97D-337820BC59E1","Mauris molestie pharetra nibh. Aliquam ornare, libero at","Ibuprofen (Rx)"),(345,"Nuvaring","A27A5356-275E-0CE0-10D2-AE0EC10DA38F","ullamcorper, velit in aliquet lobortis, nisi","Amoxicillin"),(346,"Tramadol HCl","E6F792CF-27C8-EAA0-90D8-38D7E0066BD7","molestie pharetra nibh. Aliquam ornare, libero","Lantus"),(347,"Bystolic","3A0BEEF8-C5A9-6C7A-9753-699309627B01","augue malesuada malesuada. Integer id magna et ipsum cursus","Promethazine HCl"),(348,"Sertraline HCl","F976717A-62B3-38A8-8999-03C2C2DE4739","Cras sed leo. Cras vehicula aliquet","Prednisone"),(349,"Tricor","68206916-FF7F-EF91-ABAA-880924C1A247","fames ac turpis egestas. Aliquam fringilla cursus purus.","Singulair"),(350,"Prednisone","E496A319-FFBD-4131-5883-10EE18A7895D","lacinia. Sed congue, elit sed consequat auctor, nunc nulla","Metoprolol Succinate"),(351,"Triamterene/Hydrochlorothiazide","9B892592-8C70-AE77-922B-8B0E153B3FC9","Duis elementum, dui quis accumsan convallis, ante","Metformin HCl"),(352,"Warfarin Sodium","010B3A4F-43D3-8FC6-6357-6D9DA178E870","odio. Nam interdum enim non nisi. Aenean","Amphetamine Salts"),(353,"Tricor","D0223AA7-72DC-A65B-7A19-6F0FC563862A","lorem tristique aliquet. Phasellus fermentum convallis","Sertraline HCl"),(354,"Sertraline HCl","1C0BA34E-4BEC-1C8D-2EF4-BDF3D5430943","porttitor tellus non magna. Nam ligula elit, pretium","Sertraline HCl"),(355,"Methylprednisolone","86B4F632-CA2D-15A9-BDE8-7B4A97176466","nunc sed pede. Cum sociis natoque penatibus et magnis","Spiriva Handihaler"),(356,"Sulfamethoxazole/Trimethoprim","27B9FE33-8E6A-8224-6DF6-E14F4ACD3226","dictum eleifend, nunc risus varius orci, in consequat","Citalopram HBr"),(357,"Endocet","5849F6DF-05D7-08CE-4C0E-E945B58D6B2F","nulla. In tincidunt congue turpis. In condimentum. Donec","Azithromycin"),(358,"Sulfamethoxazole/Trimethoprim","AA88DE5C-B6C8-0C3F-C33D-A964BAFF982C","sociis natoque penatibus et magnis dis parturient","Naproxen"),(359,"Premarin","613D6407-637B-6484-39D0-140A379053E6","elit. Curabitur sed tortor. Integer aliquam","Metformin HCl");
INSERT INTO `hc_product` (`id`,`display_name`,`product_code`,`description`,`brand`) VALUES (360,"Fluticasone Propionate","CBD83E85-12CF-D683-19EA-476B994A8EA0","felis orci, adipiscing non, luctus sit amet, faucibus ut,","Seroquel"),(361,"Potassium Chloride","2E6AEC5A-325E-CEF5-23E3-C3A949678C87","eget metus. In nec orci. Donec nibh. Quisque nonummy ipsum","Enalapril Maleate"),(362,"Tricor","4491FB07-066A-4CD9-2A22-E93AF51E460D","vel, convallis in, cursus et, eros. Proin ultrices. Duis","Risperidone"),(363,"Hydrocodone/APAP","46DD7431-086C-5F75-7BCE-4770C39F3672","Quisque ac libero nec ligula consectetuer","Atenolol"),(364,"Proair HFA","11DC02DD-E971-7225-7D05-B9FE9EBC5E2A","congue a, aliquet vel, vulputate eu, odio. Phasellus","Furosemide"),(365,"Ciprofloxacin HCl","030F4D25-27D5-0632-AD52-E87C584939BF","nec, imperdiet nec, leo. Morbi neque tellus,","Fluconazole"),(366,"Suboxone","67458FF2-91D2-6891-89AE-DB195F20A444","sagittis placerat. Cras dictum ultricies ligula. Nullam enim.","Fluoxetine HCl"),(367,"Prednisone","F44D1B53-5A75-F90E-515C-4D3F25503C00","sollicitudin adipiscing ligula. Aenean gravida nunc","Lisinopril"),(368,"Tricor","C2C8C168-4D4A-68E6-11A2-8F8653EE1345","feugiat placerat velit. Quisque varius. Nam porttitor","Symbicort"),(369,"Folic Acid","D6520201-37E2-EDDB-4E74-96258313C94C","eu arcu. Morbi sit amet massa. Quisque porttitor eros nec","Nexium"),(370,"Plavix","C580A665-9FAE-548D-E707-FCA42399A6EA","pede blandit congue. In scelerisque scelerisque dui. Suspendisse ac metus","Tramadol HCl"),(371,"Viagra","3E17827C-847A-3F9C-3B73-9CADA23E957B","amet, dapibus id, blandit at, nisi. Cum sociis natoque penatibus","Azithromycin"),(372,"Lexapro","47E80F7D-C971-7071-EF73-BD3E952D6877","Duis sit amet diam eu dolor egestas rhoncus. Proin","Glyburide"),(373,"Hydrochlorothiazide","91B1DB8D-7411-6F2E-7233-DDF088B716E8","purus. Maecenas libero est, congue a,","Omeprazole (Rx)"),(374,"Amphetamine Salts","F6418809-5E24-85C1-04F5-5CF6DA7E7601","diam at pretium aliquet, metus urna convallis erat, eget","Symbicort"),(375,"Fluoxetine HCl","7212B2C7-D53B-A6B3-6A73-59F5D01F2D9C","laoreet ipsum. Curabitur consequat, lectus sit amet","Lovaza"),(376,"Tricor","DFF4033F-F0A9-6BBD-5EF8-A1DC99326472","arcu. Vivamus sit amet risus. Donec egestas. Aliquam nec enim.","Methylprednisolone"),(377,"Pravastatin Sodium","3BB02DD6-4C4A-2828-4961-4AEAF5EB5FDB","lacinia vitae, sodales at, velit. Pellentesque","Flovent HFA"),(378,"Metoprolol Succinate","7A4EB2C6-B5B6-1125-799C-A7AF1DB65606","non, egestas a, dui. Cras pellentesque. Sed","Metformin HCl"),(379,"Flovent HFA","16FF608A-A1FB-1429-6134-778E5C60A198","lobortis augue scelerisque mollis. Phasellus libero mauris, aliquam eu, accumsan","Pantoprazole Sodium"),(380,"Gabapentin","68F770D8-001D-E429-724F-0E835E6D3821","et arcu imperdiet ullamcorper. Duis at lacus.","Endocet"),(381,"Prednisone","38DF4BA4-6EB1-096B-2283-B3248ADF64CB","Aliquam gravida mauris ut mi. Duis","Lisinopril"),(382,"Amlodipine Besylate","0A34C136-0C95-860B-8C93-FCA308EC67BC","diam eu dolor egestas rhoncus. Proin nisl","Seroquel"),(383,"Simvastatin","9B4833E7-73B1-98B3-A796-6FD763A8A53C","faucibus leo, in lobortis tellus justo sit","Risperidone"),(384,"Klor-Con M20","98B8EFFB-3548-0476-79C9-1107E7BE1EBD","Etiam gravida molestie arcu. Sed eu nibh vulputate","Simvastatin"),(385,"Methylprednisolone","817321AD-A4DF-FDFA-F555-CF1722C5447F","a odio semper cursus. Integer mollis. Integer tincidunt aliquam","Lisinopril"),(386,"Lisinopril","C53B3FF6-3F3E-31B1-A115-D81D4988FAA8","dictum ultricies ligula. Nullam enim. Sed nulla ante,","Sertraline HCl"),(387,"Simvastatin","4FC2F764-A794-E4D4-959B-AD3CFECD998A","non, feugiat nec, diam. Duis mi enim,","Lantus"),(388,"Metoprolol Tartrate ","393DF9A1-553A-6B52-D936-76E95A95A0C5","nunc. Quisque ornare tortor at risus.","Amlodipine Besylate"),(389,"Metoprolol Tartrate ","EED9EC82-3A5E-1389-BD68-91757B66CB3D","dictum eu, placerat eget, venenatis a, magna. Lorem ipsum","Lovaza");
INSERT INTO `hc_product` (`id`,`display_name`,`product_code`,`description`,`brand`) VALUES (390,"Warfarin Sodium","F732A352-7956-2A73-1E3C-A93F9891CE2E","diam at pretium aliquet, metus urna convallis","Amoxicillin"),(391,"Hydrochlorothiazide","CCBB96D7-7A9F-2B73-A24F-159CD0A337E7","orci tincidunt adipiscing. Mauris molestie pharetra nibh. Aliquam ornare,","Lyrica"),(392,"Simvastatin","822C44CD-5709-3DC8-2AD8-DE683F37191A","amet metus. Aliquam erat volutpat. Nulla facilisis. Suspendisse","Lisinopril"),(393,"Diovan","74472822-0140-DB95-F24D-5B605B0C53F3","augue ac ipsum. Phasellus vitae mauris sit","Tricor"),(394,"Cheratussin AC","42ACA21B-D0B1-1E38-EE47-041068D9E88E","orci. Ut sagittis lobortis mauris. Suspendisse","Metoprolol Succinate"),(395,"Proair HFA","25A68EAA-F6F9-638B-42A2-CE7B0B82A89F","eget massa. Suspendisse eleifend. Cras sed leo. Cras vehicula aliquet","Lisinopril"),(396,"Amoxicillin Trihydrate/Potassium Clavulanate","6B3E01A1-6F2A-884B-D60A-43EC200F0DBA","elementum, dui quis accumsan convallis, ante lectus convallis est, vitae","Atenolol"),(397,"Seroquel","C91BD553-1E48-0B92-2EA6-5CC1C8A6E650","ornare. In faucibus. Morbi vehicula. Pellentesque tincidunt tempus risus.","Glyburide"),(398,"Lantus Solostar","F7AB5CF9-1162-4862-FC46-D563B803866B","vel turpis. Aliquam adipiscing lobortis risus.","TriNessa"),(399,"Januvia","5B27EA66-00D5-2DA7-DA25-0021F5E374EB","nunc risus varius orci, in consequat","Flovent HFA");

insert into hc_category_products (category_id, product_id)
values('b1', 300);
insert into hc_category_products (category_id, product_id)
values('b1', 301);
insert into hc_category_products (category_id, product_id)
values('b1', 302);
insert into hc_category_products (category_id, product_id)
values('b1', 303);
insert into hc_category_products (category_id, product_id)
values('b1', 304);

insert into hc_category_products (category_id, product_id)
values('b2', 308);
insert into hc_category_products (category_id, product_id)
values('b2', 310);
insert into hc_category_products (category_id, product_id)
values('b2', 307);
insert into hc_category_products (category_id, product_id)
values('b2', 305);
insert into hc_category_products (category_id, product_id)
values('b2', 330);

insert into hc_category_products (category_id, product_id)
values('b3', 328);
insert into hc_category_products (category_id, product_id)
values('b3', 322);
insert into hc_category_products (category_id, product_id)
values('b3', 327);
insert into hc_category_products (category_id, product_id)
values('b3', 315);
insert into hc_category_products (category_id, product_id)
values('b3', 319);

insert into hc_price_list (id, base_price_list, description, locale, currency)
values('base-list', null, 'Default Price List', 'en_US', 'USD');
insert into hc_price_list (id, base_price_list, description, locale, currency)
values('sales-list', 'base-list', 'Sales Price List', 'en_US', 'USD');

INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (30,"base-list",90,300);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (31,"base-list",181,301);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (32,"base-list",126,302);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (33,"base-list",100,303);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (34,"base-list",287,304);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (35,"base-list",143,305);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (36,"base-list",234,306);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (37,"base-list",56,307);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (38,"base-list",40,308);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (39,"base-list",72,309);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (40,"base-list",150,310);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (41,"base-list",68,311);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (42,"base-list",273,312);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (43,"base-list",225,313);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (44,"base-list",189,314);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (45,"base-list",50,315);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (46,"base-list",201,316);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (47,"base-list",136,317);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (48,"base-list",184,318);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (49,"base-list",161,319);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (50,"base-list",173,320);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (51,"base-list",191,321);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (52,"base-list",36,322);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (53,"base-list",208,323);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (54,"base-list",295,324);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (55,"base-list",78,325);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (56,"base-list",168,326);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (57,"base-list",150,327);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (58,"base-list",178,328);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (59,"base-list",228,329);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (60,"base-list",110,330);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (61,"base-list",238,331);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (62,"base-list",40,332);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (63,"base-list",42,333);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (64,"base-list",197,334);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (65,"base-list",83,335);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (66,"base-list",222,336);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (67,"base-list",236,337);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (68,"base-list",100,338);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (69,"base-list",98,339);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (70,"base-list",51,340);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (71,"base-list",146,341);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (72,"base-list",278,342);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (73,"base-list",173,343);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (74,"base-list",117,344);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (75,"base-list",90,345);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (76,"base-list",205,346);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (77,"base-list",138,347);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (78,"base-list",209,348);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (79,"base-list",123,349);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (80,"base-list",233,350);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (81,"base-list",58,351);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (82,"base-list",94,352);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (83,"base-list",195,353);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (84,"base-list",246,354);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (85,"base-list",43,355);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (86,"base-list",39,356);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (87,"base-list",276,357);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (88,"base-list",249,358);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (89,"base-list",267,359);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (90,"base-list",245,360);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (91,"base-list",104,361);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (92,"base-list",30,362);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (93,"base-list",189,363);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (94,"base-list",188,364);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (95,"base-list",294,365);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (96,"base-list",195,366);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (97,"base-list",254,367);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (98,"base-list",158,368);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (99,"base-list",255,369);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (100,"base-list",74,370);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (101,"base-list",218,371);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (102,"base-list",253,372);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (103,"base-list",109,373);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (104,"base-list",70,374);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (105,"base-list",43,375);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (106,"base-list",121,376);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (107,"base-list",73,377);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (108,"base-list",164,378);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (109,"base-list",171,379);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (110,"base-list",274,380);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (111,"base-list",278,381);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (112,"base-list",32,382);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (113,"base-list",139,383);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (114,"base-list",32,384);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (115,"base-list",63,385);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (116,"base-list",264,386);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (117,"base-list",296,387);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (118,"base-list",269,388);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (119,"base-list",124,389);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (120,"base-list",115,390);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (121,"base-list",30,391);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (122,"base-list",95,392);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (123,"base-list",127,393);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (124,"base-list",34,394);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (125,"base-list",39,395);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (126,"base-list",282,396);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (127,"base-list",217,397);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (128,"base-list",236,398);
INSERT INTO `hc_price` (`id`,`price_list_id`,`list_price`,`product_id`) VALUES (129,"base-list",100,399);


insert into hc_price (id, price_list_id, list_price, product_id)
values(1, 'sales-list', 14, 300);
insert into hc_price (id, price_list_id, list_price, product_id)
values(2, 'sales-list', 10, 301);
insert into hc_price (id, price_list_id, list_price, product_id)
values(3, 'sales-list', 25, 302);
insert into hc_price (id, price_list_id, list_price, product_id)
values(4, 'sales-list', 15, 303);
insert into hc_price (id, price_list_id, list_price, product_id)
values(5, 'sales-list', 66, 304);
insert into hc_price (id, price_list_id, list_price, product_id)
values(6, 'sales-list', 26, 305);
commit;

# site
INSERT INTO `hybris`.`hs_site` (`id`, `price_list_id`, `display_name`, `locale`, `enabled`, `catalog_id`) VALUES ('hybris-site', 'sales-list', 'Hybris', 'en_US', '1', 'spices');
INSERT INTO `hybris`.`hs_site_urls` (`id`, `url`) VALUES ('hybris-site', 'localhost');

commit;