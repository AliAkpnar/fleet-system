CREATE SCHEMA park;
create table vehicle_info
(
    plate   varchar(255)   not null primary key,
    weight  decimal(20, 2) not null,
    height  decimal(20, 2) not null,
    type    int not null
);

insert into park(floor, price_per_minute, remaining_weight,  total_weight) values(1, 20, 100, 100);
insert into park(floor, price_per_minute,  remaining_weight, total_weight) values(2, 10, 150, 150);
insert into park(floor, price_per_minute,  remaining_weight, total_weight) values(3, 5, 150, 150);
insert into park(floor, price_per_minute,  remaining_weight, total_weight) values(4, 3, 250, 250);
