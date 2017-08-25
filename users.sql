create table user_info (name varchar(50) NOT NULL, emailId varchar(50) NOT NULL UNIQUE, pincode int NOT NULL, userId varchar(50) UNIQUE, PRIMARY KEY (userId));

select * from user_info;