show databases;

-- drop database project2;
create database project2;
use project2;

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
  );
  alter table employees modify phoneNo decimal(10,0) unique;

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
);
alter table stores modify phoneNo decimal(10,0) unique;
alter table stores add constraint fk_manager
foreign key(managerId) references employees(id) 
on update cascade on delete set null;

alter table employees add constraint fk_store
foreign key(storeId) references stores(id)
 on update cascade on delete restrict;


create table if not exists users(
	id bigint auto_increment primary key,
    username varchar(255) not null unique,
    password varchar(255) not null,
    roleId bigint not null default 3,
    employeeId bigint,
    foreign key(roleId) references roles(id) on update cascade,
    foreign key(employeeId) references employees(id) on delete cascade on update cascade
);

CREATE VIEW managerEmployees AS
SELECT * 
FROM employees 
WHERE designation = 'Manager';

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




create table products(
	id bigint auto_increment primary key ,
    name varchar(100) ,
    category varchar(20) ,
    subcategory varchar(20),
    brand varchar(20) ,
    size varchar(10) ,
    color varchar(20) ,
    price double not null default 0);

create table inventory(
	id bigint auto_increment ,
    productId bigint not null,
    storeId bigint not null,
    quantity bigint not null default 0,
    foreign key (storeId) references stores(id) on delete cascade on update cascade,
    foreign key (productId) references products(id) on delete cascade on update cascade,
    primary key (id));

create table suppliers(
	id bigint auto_increment primary key ,
    name varchar(50) ,
    phoneNo decimal(10,0),
    account double not null default 0,

    street varchar(50),
    city varchar(20),
    state varchar(20),
    pincode decimal(6,0));
    alter table suppliers modify phoneNo decimal(10,0) unique;
    
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
        paymentMethod varchar(20),
    payment double not null default 0,
    supplierId bigint not null,
    inventoryId bigint not null,
    foreign key (supplierId) references suppliers(id) on delete cascade on update cascade,
	foreign key (inventoryId) references inventory(id) on delete cascade on update cascade
    );

delimiter //
create trigger insertionbuy
before insert on buy
for each row
begin
    update suppliers set account=account+(new.price*new.quantity-new.payment) where id=new.supplierId;
end//
create trigger updationbuy
before update on buy
for each row
begin
	update suppliers set account=account+(old.payment-new.payment) where id=old.supplierId;
end //
delimiter ;


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
   
   alter table customers modify phoneNo decimal(10,0) unique;
    
create table customeremails(
	customerId bigint,
    customerEmail varchar(50),
    foreign key (customerId) references customers(id) on delete cascade on update cascade,
    primary key (customerId, customerEmail));

create table orders (
	id bigint auto_increment primary key,
    odate date not null,
    price double not null default 0,
    quantity bigint not null default 0,
    houseNo varchar(50),
    street varchar(50),
    city varchar(50),
    state varchar(20),
    pincode decimal(6,0),
    paymentMethod varchar(20),
    payment double not null default 0,
    employeeId bigint ,
    customerId bigint not null,
    inventoryId bigint not null,
    foreign key (customerId) references customers(id) on delete cascade on update cascade,
    foreign key (inventoryId) references inventory(id) on delete cascade on update cascade,
    foreign key (employeeId) references employees(id) on delete cascade on update cascade);

delimiter //
create trigger insertionorders
before insert on orders
for each row
begin
    update customers set account=account+(new.price*new.quantity-new.payment) where id=new.customerId;
end//
create trigger updationorders
before update on orders
for each row
begin
	update customers set account=account+(old.payment-new.payment) where id=old.customerId;
end //
delimiter ;

create table returnproducts(
	id bigint auto_increment primary key ,
    rdate date not null,
    reason varchar(200),
    amount double not null default 0,
    orderId bigint not null,
    foreign key (orderId) references orders(id) on delete cascade on update cascade);
    alter table returnproducts add quantity bigint not null default 0; 
    alter table returnproducts rename column amount to price;
   
create table feedbacks(
	id bigint auto_increment primary key ,
    fdate date not null,
    rating decimal,
    comments varchar(200),
    orderId bigint not null,
    foreign key (orderId) references orders(id) on delete cascade on update cascade);

insert into roles values(1,"ADMIN");
INSERT intO ROLES VALUES(2,"MANAGER");
INSERT intO ROLES VALUES (3,"EMPLOYEE");

create table discount(
	id bigint primary key auto_increment,
    description varchar(200),
    dos date ,
    doe date,
    rate double default 0
);

create table productDiscount(
	productId bigint not null,
    discountId bigint not null,
    primary key(productId,discountId)
);

create or replace view productD as
select p.id as id, p.name as name,p.category as category, p.subcategory as subcategory,
p.brand as brand, p.color as color, round(p.price*(1-coalesce(max(d.rate),0)/100),2) as price
from products p left join productDiscount pd on p.id=pd.productId left join discount d on pd.discountId=d.id 
group by p.id;


-- index for employees
create index idx_storeId on employees(storeId);

-- index for users
create index idx_employeeId on users(employeeId);
create index idx_username on users(username);

-- index for inventory
create index idx_storeId on inventory(storeId);

-- index for suppliers
create index idx_phoneNo on suppliers(phoneNo);

-- index for buy
create index idx_supplierId on buy(supplierId);

-- index for customers
create index idx_phoneNo on customers(phoneNo);

-- index for orders
create index idx_customerId on orders(customerId);

-- index for returnproducts
create index idx_orderId on returnproducts(orderId);

-- index for feedbacks
create index idx_orderId on feedbacks(orderId);
CREATE TABLE customer_payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT DEFAULT NULL,
    payment_amount DECIMAL(15, 2) DEFAULT 0.00,
    payment_date DATE default null,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
        ON DELETE SET NULL
);

CREATE TABLE supplier_payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    supplier_id BIGINT DEFAULT NULL,
    payment_amount DECIMAL(15, 2) DEFAULT 0.00,
    payment_date DATE default null,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
        ON DELETE SET NULL
);

create index idx_supplier_id on supplier_payment(supplier_id);
create index idx_customer_id on customer_payment(customer_id);