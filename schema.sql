show databases;

-- create database project2;
create database project2;
use project2;
-- show tables;
create table if not exists roles(
	id bigint  primary key,
    name varchar(25) not null unique
);
create table if not exists employees(
	id bigint auto_increment primary key,
    firstName varchar(20),
    middleName varchar(20),
    lastName varchar(20),
    houseNo varchar(50),
    city varchar(20),
    state varchar(20),
    pincode decimal(6,0),
    designation varchar(20),
    phoneNo decimal(10,0),
    emailId varchar(50),
    storeId bigint,
    dob date,
    sex enum('M','F') not null default 'M',
    salary double default 0,
    supervisor bigint ,
    foreign key(supervisor) references employees(id) on update cascade on delete cascade
   --  ,foreign key(storeId) references stores(id) on update cascade on delete set null
);
-- desc employees;
CREATE table if not exists stores(
	id bigint auto_increment primary key,
    name varchar(50) not null,
    street varchar(50),
	state varchar(20),
    city varchar(20),
    pincode decimal(6,0),
    emailId varchar(50),
    phoneNo decimal(10,0),
    managerId bigint
    -- ,foreign key(managerId) references employees(id) on update cascade on delete set null
);
alter table stores add constraint fk_manager
foreign key(managerId) references employees(id) 
on update cascade on delete set null;
-- alter table employees drop foreign key fk_store;
alter table employees add constraint fk_store
foreign key(storeId) references stores(id)
 on update cascade on delete restrict;
-- alter table employees add dob Date;
-- describe employees;

-- alter table employees modify column sex enum('M','F') not null;



-- show tables;
create table if not exists users(
	id bigint auto_increment primary key,
    username varchar(255) not null unique,
    password varchar(255) not null,
    roleId bigint not null default 3,
    employeeId bigint,
    foreign key(roleId) references roles(id) on update cascade,
    foreign key(employeeId) references employees(id) on delete cascade on update cascade
);
-- show tables;
-- desc users;
-- alter table users modify column roleId bigint not null default 3;
-- select * from roles;
-- insert biginto users (username,password,roleId) values('admin','admin','3');


-- delete from users where id=5;

-- show tables;
-- select * from employees;
-- desc users;
-- SELECT 
--     CONSTRAbigint_NAME,
--     COLUMN_NAME,
--     REFERENCED_TABLE_NAME,
--     REFERENCED_COLUMN_NAME
-- FROM 
--     information_schema.KEY_COLUMN_USAGE
-- WHERE 
--     TABLE_NAME = 'users'
--     AND TABLE_SCHEMA = 'project';

-- alter table users drop foreign key users_ibfk_1;
-- alter table roles modify column id bigint;
-- alter table users add constrabigint users_ibfk_1 foreign key (roleId) references roles(id) on update cascade;


-- alter table stores modify pincode bigint(6);
-- alter table employees drop column salary;
-- alter table employees add salary double default 0;
-- desc stores;
-- desc roles;
-- desc users;
-- alter table employees modify column designation varchar(255);
-- select * from users;
CREATE VIEW managerEmployees AS
SELECT * 
FROM employees 
WHERE designation = 'Manager';
-- show triggers;
-- desc stores;
-- drop trigger designationInsertionMatch;
delimiter //
CREATE TRIGGER designationInsertionMatch
BEFORE INSERT ON stores
FOR EACH ROW
BEGIN
	DECLARE designa VARCHAR(20);
	if new.managerId is not null then
		SELECT designation into designa FROM employees WHERE id = NEW.managerId;
		IF designa != 'Manager' THEN
			SIGNAL SQLSTATE '45000' 
			SET MESSAGE_TEXT = 'Only employees with Manager designation can be assigned as store manager';
		END IF;
    end if ;
END //
delimiter ;
-- show tables;
-- desc manageremployees;
-- desc employees;
-- show triggers;
-- drop trigger designationUpdationMatch;
delimiter //
CREATE TRIGGER designationUpdationMatch
BEFORE UPDATE ON stores
FOR EACH ROW
BEGIN
	DECLARE designa VARCHAR(255) default "Employee";
	if new.managerId is not null then
		SELECT designation into designa FROM employees WHERE id = NEW.managerId;
		IF designa != 'Manager' THEN
			SIGNAL SQLSTATE '45000' 
			SET MESSAGE_TEXT = 'Only employees with Manager designation can be assigned as store manager or there is already a manager for this store';
		END IF;
    end if ;
    if old.managerId is not null then
		update employees set designation="Senior Employee" where id=old.managerId;
    end if;
END //
delimiter ;

-- show triggers;
-- show full tables;


-- show full tables;
-- desc stores;
-- alter table employees modify column firstName varchar(20);
-- alter table employees modify column middleName varchar(20);
-- alter table employees modify column lastName varchar(20);
-- alter table employees modify column houseNo varchar(50);
-- alter table employees modify column city varchar(20);
-- alter table employees modify column state varchar(20);
-- alter table employees modify column  pincode bigint(6);
-- alter table employees modify column designation varchar(20);
-- alter table employees modify column emailId varchar(50);
-- alter table employees  add salary double;
-- alter table stores modify column name varchar(50);
-- alter table stores modify column street varchar(50);
-- alter table stores modify column state varchar(20);
-- alter table stores modify column city varchar(20);
-- alter table stores modify column emailId varchar(50);
-- alter table stores modify column pincode bigint(6);
-- select * from users;
-- insert biginto users(username,password,roleId) values("admin1","admin1",1);

show databases;


-- create table supervisors (
-- 	worker bigint,
--     supervisor bigint,
--     foreign key (worker) references employees(id) on delete cascade on update cascade,
--     foreign key (supervisor) references employees(id) on delete cascade on update cascade,
--     primary key (worker,supervisor));
-- create table store(
-- 	storeid bigint primary key auto_increment,
--     name varchar(50),
--     start_time TIME NOT NULL,
--     end_time TIME NOT NULL,
--     phoneno bigint,
--     street varchar(50),
--     City varchar(20),
--     State varchar(20),
--     pincode bigint not null);
-- create table storemails(
-- 	storeid bigint,
--     storeemail varchar(100),
--     foreign key (storeid) references store(storeid) on delete cascade on update cascade,
--     primary key (storeid, storeemail));
    
-- alter table employee
-- add store_id bigint,
-- add constrabigint fk_employee
-- foreign key (store_id) references store(storeid) on delete cascade on update cascade;
-- desc employee;

-- alter table store
-- add manager bigint,
-- add constrabigint fk_store
-- foreign key (manager) references employee(empid) on delete cascade on update cascade;

create table products(
	id bigint auto_increment primary key ,
    name varchar(100) ,
    category varchar(20) ,
    subcategory varchar(20),
    brand varchar(20) ,
    size varchar(10) ,
    color varchar(20) ,
    price double not null default 0);
    
--     alter table products modify column id bigint auto_increment;
--     alter table products rename column  productName to name;
--     insert biginto products(name) values("product1"); 

create table inventory(
	id bigint auto_increment ,
    productId bigint not null,
    storeId bigint not null,
    quantity bigint not null default 0,
    foreign key (storeId) references stores(id) on delete cascade on update cascade,
    foreign key (productId) references products(id) on delete cascade on update cascade,
    primary key (id));
    
-- desc inventory;
-- select * from stores;
-- insert biginto inventory(productId,storeId,quantity) values(1,4,10);
-- alter table inventory modify column id bigint;
create table suppliers(
	id bigint auto_increment primary key ,
    name varchar(50) ,
    phoneNo decimal(10,0),
    account double not null default 0,
--     emailIds bigint ,
    street varchar(50),
    city varchar(20),
    state varchar(20),
    pincode decimal(6,0));
    alter table suppliers modify column account double default 0;
create table supplierMails(
	supplierId bigint,
    supplierEmail varchar(50),
    foreign key (supplierId) references suppliers(id) on delete cascade on update cascade,
    primary key (supplierId, supplierEmail));
    
create table buy (
	id bigint auto_increment primary key ,
    dop date not null ,
    price double not null default 0,
    quantity bigint not null default 0,
    -- totalAmount bigint not null default 0, in java model,not in database
        paymentMethod varchar(20),
    payment double not null default 0,
    supplierId bigint not null,
    -- storeId bigint not null,
    inventoryId bigint not null,
    foreign key (supplierId) references suppliers(id) on delete cascade on update cascade,
	foreign key (inventoryId) references inventory(id) on delete cascade on update cascade
   --  foreign key(storeId) references stores(id) on delete cascade on update cascade
    );


-- create table accountsupplier(
-- 	id bigint primary key auto_increment,
--     supplierId bigint not null,
--     amount double not null default 0,
--     foreign key (supplierId) references suppliers(id)
-- );
-- drop trigger insertionorders;
delimiter //
create trigger insertionbuy
before insert on buy
for each row
begin
	-- declare idd bigint default 0;
--     select id into idd from accountsupplier where supplierId=new.supplierId;
--     if idd=0 then
-- 		insert into accountsupplier(supplierId,amount) values(new.supplierId,new.price*new.quantity-new.payment);
--     else
-- 		update accountsupplier set amount=amount+(new.price*new.quantity-new.payment) where id=new.supplierId;
--     end if;
    update suppliers set account=account+(new.price*new.quantity-new.payment) where id=new.supplierId;
end//
create trigger updationbuy
before update on buy
for each row
begin
	update suppliers set account=account+(old.payment-new.payment) where id=old.supplierId;
end //
delimiter ;
show triggers;
-- desc buy;
-- show tables;
create table customers(
	id bigint auto_increment primary key ,
    firstName varchar(20),
    middleName varchar(20),
    lastName varchar(20),
    phoneNo decimal(10,0),
    account double not null default 0,
    houseNo varchar(50),
    -- street varchar(50),
    city varchar(20),
    state varchar(20),
    pincode decimal(6,0),
    sex ENUM('M', 'F', 'Other') NOT NULL default 'M',
    dob DATE );
    alter table customers modify column account double default 0;
create table customeremails(
	customerId bigint,
    customerEmail varchar(50),
    foreign key (customerId) references customers(id) on delete cascade on update cascade,
    primary key (customerId, customerEmail));
    -- drop table order_items;
-- create table order_items(
-- 	orderId bigint ,
--     
--     primary key(orderId,inventoryId)
-- );
-- alter table order_items add constrabigint pk1 primary key(orderId,inventoryId);
create table orders (
	id bigint auto_increment primary key,
    odate date not null,
    price double not null default 0,
    quantity bigint not null default 0,
    -- totalcost bigint not null,
    houseNo varchar(50),
    street varchar(50),
    city varchar(50),
    state varchar(20),
    pincode decimal(6,0),
    paymentMethod varchar(20),
    payment double not null default 0,
    employeeId bigint ,
    customerId bigint not null,
    -- storeId bigint not null,
    inventoryId bigint not null,
    foreign key (customerId) references customers(id) on delete cascade on update cascade,
    foreign key (inventoryId) references inventory(id) on delete cascade on update cascade,
    -- foreign key (storeId) references stores(id) on delete cascade on update cascade,
    foreign key (employeeId) references employees(id) on delete cascade on update cascade);
-- desc orders;
-- create table accountcustomer(
-- 	id bigint primary key auto_increment,
--     customerId bigint  not null,
--     amount double not null default 0,
--     foreign key (customerId) references customers(id)
-- );
-- alter table orders modify column employeeId bigint ;
desc orders;
-- show triggers;
-- drop trigger updationbuy;
delimiter //
create trigger insertionorders
before insert on orders
for each row
begin
	-- declare idd bigint default 0;
--     select id into idd from accountcustomer where customerId=new.customerId;
--     if idd=0 then
-- 		insert into accountcustomer(customerId,amount) values(new.customerId,new.price*new.quantity-new.payment);
--     else
-- 		update accountcustomer set amount=amount+(new.price*new.quantity-new.payment) where id=new.customerId;
--     end if;
    update customers set account=account+(new.price*new.quantity-new.payment) where id=new.customerId;
end//
create trigger updationorders
before update on orders
for each row
begin
	update customers set account=account+(old.payment-new.payment) where id=old.customerId;
end //
delimiter ;
show triggers;
create table returnproducts(
	id bigint auto_increment primary key ,
    rdate date not null,
    reason varchar(200),
    amount double not null default 0,
    orderId bigint not null,
    foreign key (orderId) references orders(id) on delete cascade on update cascade);
    alter table returnproducts add quantity bigint not null default 0; 
    alter table returnproducts rename column amount to price;
    use project2;
 desc returnproducts;
create table feedbacks(
	id bigint auto_increment primary key ,
    fdate date not null,
    rating decimal,
    comments varchar(200),
    orderId bigint not null,
    foreign key (orderId) references orders(id) on delete cascade on update cascade);
-- desc feedback;
-- create table discounts(
-- 	id bigint primary key not null,
--     descriptions varchar(50),
--     startDate date not null,
--     endDate date not null,
--     rate decimal);
-- -- desc discount;
-- create table productdiscount (
-- 	 productId bigint not null,
--      discountId bigint not null,
--      foreign key (productId) references products(id) on delete cascade on update cascade,
--      foreign key (discountId) references discounts(id) on delete cascade on update cascade,
--      primary key (productId, discountId));
-- desc productdiscount;
-- desc roles;
insert into roles values(1,"ADMIN");
INSERT intO ROLES VALUES(2,"MANAGER");
INSERT intO ROLES VALUES (3,"EMPLOYEE");

-- use project2;
show triggers;
