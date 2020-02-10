create database Auto_Sales_db;

create table engine (
	name_engine varchar(50) primary key
);

create table carbody (
	name_carbody varchar(50) primary key
);

create table transmission (
	name_transmission varchar(50) primary key
);

create table roles (
	name_role varchar(50) primary key,
	main_per boolean,
	admin_per boolean
);

create table users (
	user_id serial primary key,
	password_user INTEGER  not null,
	name_user varchar(50) unique not null,
	role_user varchar references roles(name_role)
);

create table car (
	car_id serial primary key,
	engine varchar references engine(name_engine),
	carbody varchar references carbody(name_carbody),
	transmission varchar references transmission(name_transmission)
);
create table advertisement (
	ad_id serial primary key,
	ad_shortname varchar(50),
	ad_description varchar(100),
	ad_photo varchar(50),
	ad_time timestamp,
	id_creator integer references users(user_id) not null,
	id_car integer references car(car_id),
	ad_status boolean
);

insert into roles (name_role, main_per, admin_per) values ('Admin', true, true);
insert into roles (name_role, main_per, admin_per) values ('Owner', true, false);

insert into engine values('v4');
insert into engine values('v6');
insert into engine values('v8');

insert into carbody values('sedan');
insert into carbody values('truck');
insert into carbody values('coupe');
insert into carbody values('van');

insert into transmission values('AT');
insert into transmission values('MT');
insert into transmission values('CVT');