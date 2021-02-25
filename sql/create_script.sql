SET NAMES utf8;
ALTER TABLE orders
  DROP FOREIGN KEY orders_ibfk_1;
ALTER TABLE orders
  DROP FOREIGN KEY orders_ibfk_2;
ALTER TABLE exposition_halls
  DROP FOREIGN KEY exposition_halls_ibfk_1;
ALTER TABLE exposition_halls
  DROP FOREIGN KEY exposition_halls_ibfk_2;  
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `login` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `passhash` varchar(355) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `access_level` enum('admin','user') CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(355) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_language` enum('en','ru') CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `date_in` date NOT NULL,
  `date_out` date NOT NULL,
  `exposition_id` int DEFAULT NULL,
  `additional_info` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `cost` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `exposition_id` (`exposition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `exposition`;
CREATE TABLE `exposition` (
  `id` int NOT NULL AUTO_INCREMENT,
  `theme` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
--   `exposition_halls_id` int NOT NULL,
  `ticket_price` int NOT NULL,
  `date_in` date NOT NULL,
  `date_out` date NOT NULL,
  `tickets_count` int NOT NULL,
  `img_name` varchar(255) null,
  PRIMARY KEY (`id`)-- ,
--   KEY `exposition_halls_id` (`exposition_halls_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `exposition_halls`;
CREATE TABLE `exposition_halls` (
  `id` int NOT NULL AUTO_INCREMENT,
  `exposition_id` int NOT NULL,
  `halls_id` int NULL,
  PRIMARY KEY (`id`)
--   KEY `halls_id` (`halls_id`),
--   KEY `exposition_id` (`exposition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `halls`;
CREATE TABLE `halls` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hall_name` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX exposition_id ON exposition_halls(exposition_id);
CREATE INDEX halls_id ON exposition_halls(halls_id);
ALTER TABLE orders ADD FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE orders ADD FOREIGN KEY (exposition_id) REFERENCES exposition (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE exposition_halls ADD FOREIGN KEY (exposition_id) REFERENCES exposition (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE exposition_halls ADD FOREIGN KEY (halls_id) REFERENCES halls (id) ON DELETE SET NULL ON UPDATE CASCADE;



-- insert into users
insert into users (email, login, passhash, access_level, name, user_language) values ('admin@admin.com', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin', 'admin', 'ru');
insert into users (email, login, passhash, access_level, name, user_language) values ('grpetr189853@gmail.com', 'grpetr189853', 'b557ccf4f6f39b34d126a3bf4d730956', 'user', 'grpetr189853', 'en');
insert into users (email, login, passhash, access_level, name, user_language) values ('exposition_fan@gmail.com', 'exposition_fan', 'b557ccf4f6f39b34d126a3bf4d730956', 'user', 'exposition_fan', 'en');


-- INSERT INTO EXPOSITIONS--
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Мой путь, то костер, то георгины", 500, "2008-10-23","2008-10-23",500,"1.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Ракеты, Украина и космос - всеукраинский конкурс детских рисунков", 800, "2020-02-03","2020-03-09",700,"2.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Динозавры морских глубин", 300, "2020-06-15","2020-10-11",600,"3.jpeg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Постояннодействующая‌ ‌интерактивная‌ ‌‌Выставка-музей‌ ‌камня‌ ‌'Минералия'", 340, "2020-05-12","2020-11-17",600,"4.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Екатерина Билокур. Жизнь в объятиях цветов", 550, "2020-06-13","2020-12-12",800,"5.jpeg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Творческая сила свободы. Инициативы Майдана", 350, "2020-04-08","2020-07-15",300,"6.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка собак 'Осінній Львів 2019'", 10, "2020-10-19","2020-10-20",300,"7.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Дентал-Україна (міжнародна стоматологічна виставка)", 20, "2019-10-23","2019-10-25",300,"8.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка Володимира Жишковича 'Екстраполяція реальності'", 20, "2021-02-18","2021-03-09",300,"9.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка 'Чому у Львові будуть художниці'", 250, "2020-11-23","2021-02-18",350,"10.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка Ярини Мовчан 'Мерехтіння'", 250, "2021-01-26","2021-02-26",560,"11.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка Дзвінки Загайської 'Видимо-невидимо'", 250, "2021-01-26","2021-02-26",560,"12.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка 'Образи' Роксолани Табаки", 270, "2021-02-10","2021-02-21",520,"13.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка Ірини Данилів-Флінти 'Малярство'", 320, "2021-02-05","2021-02-28",820,"14.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка Бориса Фірцака 'Портретація'", 350, "2021-01-29","2021-02-28",400,"15.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Проєкт 'EXIsT' Данила Туркіна", 500, "2021-01-26","2021-02-28",300,"16.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка 'R³: Сергій, Ніна, Катерина Резніченко'", 700, "2021-02-16","2021-03-01",300,"17.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка Ярослава Качмара 'Таїнство'", 900, "2021-01-30","2021-03-01",100,"18.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка Ростислава Котерліна 'Майбутнє перед минулим'", 400, "2021-02-03","2021-03-03",50,"19.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Постійно діючі експозиції та виставки у музеях Львова", 480, "2021-01-25","2021-01-31",200,"20.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка 'Мирон Кипріян. Життя проведене в театрі'", 480, "2021-01-25","2021-11-16",200,"21.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Проект Аготи Вереш 'Зелений щоденник'", 430, "2021-01-25","2021-11-10",240,"22.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка Михайла Демцю", 430, "2021-01-01","2021-12-06",240,"23.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка 'Вічно жива натура'", 230, "2021-01-01","2021-11-15",500,"24.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Виставка Софії Сулій 'Сад зі слів і вогню'", 250, "2021-01-01","2021-11-08",300,"25.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("'Україно моя', або захисникам українських рубежів присвячується", 550, "2017-07-10","2017-12-31",200,"26.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("Фотовиставка 'Люди, які творять Органний'", 240, "2021-02-04","2017-02-28",200,"27.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("ЮВЕЛІРНА ВИСТАВКА 'ЕЛІТЕКСПО'", 900, "2021-02-25","2021-02-28",100,"28.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("ВИСТАВКА 'МОДЕСТ СОСЕНКО. ФРАГМЕНТИ: ДО МОНУМЕНТАЛЬНОГО'", 900, "2020-12-22","2021-02-28",100,"29.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("ВИСТАВКА '1939. ПОЧАТОК'", 900, "2020-02-18","2021-03-18",100,"30.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("ФОТОПРОЄКТ '8 МІЛЬЯРДІВ ПРИЧИН БУТИ ВІЛЬНИМИ'", 900, "2020-02-20","2021-03-08",100,"31.jpg");
insert into exposition (theme, ticket_price, date_in , date_out, tickets_count, img_name) values("БОРИС ФІРЦАК. ПОРТРЕТАЦІЯ", 900, "2020-02-29","2021-03-01",100,"32.jpg");

-- INSERT INTO HALLS

insert into halls (hall_name) values ("Павла Тычины (Литературно-мемориальный музей-квартира П.Г. Тычины)");
insert into halls (hall_name) values ("МВЦ");
insert into halls (hall_name) values ("ВДНГ на Глушкова");
insert into halls (hall_name) values ("МИНЕРАЛИЯ");
insert into halls (hall_name) values ("Музей украинского народного декоративного искусства");
insert into halls (hall_name) values ("Музей книги и книгопечатания Украины");
insert into halls (hall_name) values ("Велотрек СКА");
insert into halls (hall_name) values ("Львівський палац мистецтв");
insert into halls (hall_name) values ("Арт салон Veles");
insert into halls (hall_name) values ("Lviv Art Center");
insert into halls (hall_name) values ("Галерея сучасного сакрального мистецтва 'IconArt'");
insert into halls (hall_name) values ("Мистецька галерея 'Зелена Канапа'");
insert into halls (hall_name) values ("Галерея Львівської національної академії мистецтв");
insert into halls (hall_name) values ("Відділ НМЛ ім. Андрея Шептицького");
insert into halls (hall_name) values ("Львівська національна галерея мистецтв ім. Бориса Возницького");
insert into halls (hall_name) values ("Арт-кав’ярня 'Квартира 35'");
insert into halls (hall_name) values ("PM gallery");
insert into halls (hall_name) values ("Музей 'Іоана Георгія Пінзеля'");
insert into halls (hall_name) values ("Арт-центр Павла Гудімова Я Галерея");
insert into halls (hall_name) values ("Музеї міста");
insert into halls (hall_name) values ("Національний музей у Львові ім. Андрея Шептицького");
insert into halls (hall_name) values ("Галерея 'Дзиґа'");
insert into halls (hall_name) values ("Національний музей у Львові ім. Андрея Шептицького");
insert into halls (hall_name) values ("Галерея 'Зелена Канапа'");
insert into halls (hall_name) values ("Галерея сучасного сакрального мистецтва 'IconArt'");
insert into halls (hall_name) values ("Львівський музей історії релігії");
insert into halls (hall_name) values ("Будинок органної та камерної музики / Lviv Concert House");
insert into halls (hall_name) values ("Львівський палац мистецтв\ Lviv Art Palace");
insert into halls (hall_name) values ("Національний музей у Львові імені Андрея Шептицького");
insert into halls (hall_name) values ("Львівський національний літературно-меморіальний музей Івана Франка");
insert into halls (hall_name) values ("Галерея 'Дзиґа'");
insert into halls (hall_name) values ("Львівська національна галерея мистецтв ім. Б. Г. Возницького");

-- insert into exposition_halls

insert into exposition_halls (exposition_id, halls_id) values (1,1);
insert into exposition_halls (exposition_id, halls_id) values (2,2);
insert into exposition_halls (exposition_id, halls_id) values (3,3);
insert into exposition_halls (exposition_id, halls_id) values (4,4);
insert into exposition_halls (exposition_id, halls_id) values (5,5);
insert into exposition_halls (exposition_id, halls_id) values (6,6);
insert into exposition_halls (exposition_id, halls_id) values (7,7);
insert into exposition_halls (exposition_id, halls_id) values (8,8);
insert into exposition_halls (exposition_id, halls_id) values (9,9);
insert into exposition_halls (exposition_id, halls_id) values (10,10);
insert into exposition_halls (exposition_id, halls_id) values (11,11);
insert into exposition_halls (exposition_id, halls_id) values (12,12);
insert into exposition_halls (exposition_id, halls_id) values (13,13);
insert into exposition_halls (exposition_id, halls_id) values (14,14);
insert into exposition_halls (exposition_id, halls_id) values (15,15);
insert into exposition_halls (exposition_id, halls_id) values (16,16);
insert into exposition_halls (exposition_id, halls_id) values (17,17);
insert into exposition_halls (exposition_id, halls_id) values (18,18);
insert into exposition_halls (exposition_id, halls_id) values (19,19);
insert into exposition_halls (exposition_id, halls_id) values (20,20);
insert into exposition_halls (exposition_id, halls_id) values (21,21);
insert into exposition_halls (exposition_id, halls_id) values (22,22);
insert into exposition_halls (exposition_id, halls_id) values (23,23);
insert into exposition_halls (exposition_id, halls_id) values (24,24);
insert into exposition_halls (exposition_id, halls_id) values (25,25);
insert into exposition_halls (exposition_id, halls_id) values (26,26);
insert into exposition_halls (exposition_id, halls_id) values (27,27);
insert into exposition_halls (exposition_id, halls_id) values (28,28);
insert into exposition_halls (exposition_id, halls_id) values (29,29);
insert into exposition_halls (exposition_id, halls_id) values (30,30);
insert into exposition_halls (exposition_id, halls_id) values (31,31);
insert into exposition_halls (exposition_id, halls_id) values (32,32);
