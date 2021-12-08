create schema if not exists erest;

create TABLE IF NOT EXISTS erest.user (
	id uuid NOT NULL DEFAULT random_uuid(),
    username varchar(16),
	password varchar(40),
	firstname varchar(16),
	lastname varchar(16),
	email varchar(24),
	phone varchar(24),
	userstatus varchar(24),
	role varchar(16),
	booking_id uuid,
	PRIMARY KEY(id)
);

create TABLE IF NOT EXISTS erest.booking (
	id uuid NOT NULL DEFAULT random_uuid(),
    booking_date timestamp,
    seats_num numeric(30, 1),
    customer_id uuid NOT NULL,
    restaurant_id uuid NOT NULL,
    PRIMARY KEY(id)
);


create TABLE IF NOT EXISTS erest.rest_table (
    id uuid NOT NULL DEFAULT random_uuid(),
    name varchar(24),
    table_seats numeric(10, 1),
    restaurant_id uuid NOT NULL,
    service_id uuid,
    upgrades_id uuid,
    PRIMARY KEY(id)
);



create TABLE IF NOT EXISTS erest.upgrades (
    id uuid NOT NULL DEFAULT random_uuid(),
    quantity numeric(8, 0),
    unit_price numeric(16, 4),
    drink varchar(16),
    special_food varchar(16),
    dietary varchar(16),
    waiter_id uuid,
    rest_table_id uuid NOT NULL,
    PRIMARY KEY(id)
);

create TABLE IF NOT EXISTS erest.waiter (
    id uuid NOT NULL DEFAULT random_uuid(),
    fullname varchar(16),
    rest_id uuid NOT NULL,
    service_id uuid,
    upgrades_id uuid,
    PRIMARY KEY(id)
);

create TABLE IF NOT EXISTS erest.menu (
    id uuid NOT NULL DEFAULT random_uuid(),
    food varchar(32),
    drink varchar(32),
	quantity numeric(8, 0),
    unit_price numeric(16, 4),
    picture varchar(1000),
    restaurant_id uuid NOT NULL,
    PRIMARY KEY(id)
);

create TABLE IF NOT EXISTS erest.restaurant (
    id uuid NOT NULL DEFAULT random_uuid(),
    tables_num numeric(200, 1) NOT NULL,
    restname varchar(24),
    hystory varchar(1000),
    address varchar(24),
	city varchar(24),
    email varchar(24),
    phone varchar(24),
    picture varchar(1000),
    customer_id uuid,
    table_id uuid,
    waiter_id uuid,
    menu_id uuid,
    PRIMARY KEY(id),
    FOREIGN KEY(customer_id)
        REFERENCES erest.user(id),
    FOREIGN KEY(table_id)
        REFERENCES erest.rest_table(id),
    FOREIGN KEY(waiter_id)
        REFERENCES erest.waiter(id),
    FOREIGN KEY(menu_id)
        REFERENCES erest.menu(id)
);

INSERT INTO erest.restaurant (id, tables_num, restname, hystory, address, city, email, phone, picture) VALUES ('24fe6833-97f4-4356-a133-3a84cdd20596', 30, 'VILLA ITALIA', 'Villa Italia is a family run pizzeria restaurant established in 1988 in the heart of South Belfast, just a stones throw from Queens University.',
'41 University Rd', 'Belfast', '+4402890328356', 'info@villaitalia.co.uk', 'https://www.thebalancesmb.com/thmb/kectf9d4azgI8yVnBuoB0h2Z8zA=/3865x2174/smart/filters:no_upscale()/overhead-view-of-smiling-female-friends-sharing-lunch-in-restaurant-928010348-5b4abe8f46e0fb003712c478.jpg');

create TABLE IF NOT EXISTS erest.service (
    id uuid NOT NULL DEFAULT random_uuid(),
    waiter_id uuid,
    rest_table_id uuid NOT NULL,
    booking_id uuid NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(booking_id)
        REFERENCES erest.booking(id),
    FOREIGN KEY(rest_table_id)
        REFERENCES erest.rest_table(id),
    FOREIGN KEY(waiter_id)
        REFERENCES erest.waiter(id)
);

create TABLE IF NOT EXISTS erest.user_token (
 id uuid NOT NULL DEFAULT random_uuid(),
 refresh_token varchar(128),
 user_id uuid NOT NULL,
 PRIMARY KEY(id),
 FOREIGN KEY (user_id)
  REFERENCES erest.user(id)
);

alter TABLE erest.user
    add FOREIGN KEY(booking_id)
    REFERENCES erest.booking(id);

alter TABLE erest.booking
    add FOREIGN KEY(customer_id)
    REFERENCES erest.user(id);

alter TABLE erest.rest_table
    add FOREIGN KEY(restaurant_id)
    REFERENCES erest.restaurant(id);

alter TABLE erest.rest_table
    add FOREIGN KEY(service_id)
    REFERENCES erest.service(id);

alter TABLE erest.rest_table
    add FOREIGN KEY(upgrades_id)
    REFERENCES erest.upgrades(id);

alter TABLE erest.upgrades
    add FOREIGN KEY(waiter_id)
    REFERENCES erest.waiter(id);

alter TABLE erest.upgrades
    add FOREIGN KEY(rest_table_id)
    REFERENCES erest.rest_table(id);

alter TABLE erest.waiter
    add FOREIGN KEY(rest_id)
    REFERENCES erest.restaurant(id);

alter TABLE erest.waiter
    add FOREIGN KEY(service_id)
    REFERENCES erest.service(id);

alter TABLE erest.waiter
    add FOREIGN KEY(upgrades_id)
    REFERENCES erest.upgrades(id);

alter TABLE erest.menu
    add FOREIGN KEY(restaurant_id)
    REFERENCES erest.restaurant(id);

INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('748acfcd-78e1-426f-9d54-9797871c51d7', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('2df1a409-ea40-45a1-92b2-0d2146bb007a', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('ac9b0060-b966-4276-ac2c-48e6fd4e9167', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('5b809449-274a-4989-83ee-91ec67f4294d', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('98c9df35-14b2-4c7b-9bce-49fa5de7e5d4', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('7960bb87-3426-4b83-bda1-1f7ff8612722', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('bb419456-cf87-4ba8-a094-0459dc264c1b', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('9ded899d-0b76-4d3f-ac98-aafb06bec988', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('8dbc9301-92a6-4400-b18f-2ea21f493b45', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('0b38c30e-4958-442e-aacb-cb430e2f1d5e', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('9b3168e0-57c7-4c39-8597-ca5875604891', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('7b42678c-5217-4d14-a3b3-d8cc50a8ee7a', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('60a9bf5a-763d-4970-a6ae-df6fa7916619', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('144537d6-f661-4e0f-9ce1-ee96d8a063bd', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('496cfc20-cfe5-4322-9cca-dfdf14310fc7', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('02ce385f-9693-44d0-a980-e4c8c45a6e09', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('8182e40b-3546-47b4-a0ac-fb5401bfe27a', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('c500d75f-4d8a-45a3-bd8e-c51102eeb5a5', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('85392cf0-a08e-46ce-aaff-e4d7517b515b', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('2eaec69b-1ead-4cd3-b704-29fe616ac38d', 2, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('0d218f29-aedc-4b8a-bc30-c9f43b4d39eb', 4, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('bc4e95ad-f252-4cd6-ae3d-b702b23bef45', 4, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('4063a3a8-f2fa-4f15-bc8d-1ce9c59e328f', 4, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('c20bc1db-97af-475a-a931-6f5f5c2c2613', 4, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('40ca43ea-8fbe-4195-ae4c-1a7d3f665bf2', 4, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('47adfd54-5f12-4d17-941d-c0423871fa7b', 4, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, table_seats, restaurant_id) VALUES ('e3b7bd54-22be-4021-af28-f3405d29915f', 4, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, name,table_seats, restaurant_id) VALUES ('b9648730-ad49-4e32-b294-55e64bd31f3c', 'Sea View', 6, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, name, table_seats, restaurant_id) VALUES ('04124ee3-d556-4f47-946b-88eeb779898d', 'Mountain View', 6, '24fe6833-97f4-4356-a133-3a84cdd20596');
INSERT INTO erest.rest_table (id, name, table_seats, restaurant_id) VALUES ('b8bdb03e-d9f8-491c-8504-61cdbe673477', 'The Grand Duke', 8, '24fe6833-97f4-4356-a133-3a84cdd20596');



--create TABLE IF NOT EXISTS erest.rest_table (
--    name
--    id uuid NOT NULL DEFAULT random_uuid(),
--    table_seats numeric(10, 1),
--    restaurant_id uuid NOT NULL,
--    service_id uuid,
--    upgrades_id uuid,
--    PRIMARY KEY(id)
--);