create schema if not exists erest;

create TABLE IF NOT EXISTS erest.user (
	id uuid NOT NULL DEFAULT random_uuid(),
    username varchar(16),
	password varchar(40),
	firstname varchar(16),
	lastname varchar(16),
	email varchar(24),
	phone varchar(24),
	booking_id uuid NOT NULL,
	PRIMARY KEY(id)
);

create TABLE IF NOT EXISTS erest.booking (
	id uuid NOT NULL DEFAULT random_uuid(),
    booking_date timestamp,
    seats_num numeric(30, 1),
    customer_id uuid NOT NULL,
    service_id uuid NOT NULL,
    PRIMARY KEY(id)
);


create TABLE IF NOT EXISTS erest.rest_table (
    id uuid NOT NULL DEFAULT random_uuid(),
    table_seats numeric(10, 1),
    rest_id uuid NOT NULL,
    service_id uuid NOT NULL,
    upgrades_id uuid NOT NULL,
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
    rest_id uuid NOT NULL,
    PRIMARY KEY(id)
);

create TABLE IF NOT EXISTS erest.restaurant (
    id uuid NOT NULL DEFAULT random_uuid(),
    tables_num numeric(200, 1) NOT NULL,
    hystory varchar(1000),
    address varchar(24),
	city varchar(24),
    email varchar(24),
    phone varchar(24),
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

alter TABLE erest.booking
    add FOREIGN KEY(service_id)
    REFERENCES erest.service(id);

alter TABLE erest.rest_table
    add FOREIGN KEY(rest_id)
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
    add FOREIGN KEY(rest_id)
    REFERENCES erest.restaurant(id);