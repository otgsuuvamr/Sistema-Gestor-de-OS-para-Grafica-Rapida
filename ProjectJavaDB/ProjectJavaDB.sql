create database dbsyscontroll;
use dbsyscontroll;

create table userinfo(
iduser int primary key,
usr varchar(20) not null,
phoneusr varchar(15),
login varchar(20) not null unique,
pswrd varchar(20) not null
);
describe userinfo;

insert into userinfo(iduser, usr, phoneusr, login, pswrd)
values(1,'admin','2348-3046','admin','adm220181');
select * from userinfo;

create table clientes(
idcli int primary key auto_increment,
nomecli varchar(50) not null,
phonecli varchar(15) not null
);
describe clientes;

create table osinfo(
os int primary key auto_increment,
data_os timestamp default current_timestamp,
produto varchar(150) not null,
quantidade int not null,
funcionario varchar(30),
valor decimal (10,2),
idcli int not null,
foreign key(idcli) references clientes(idcli)
);
describe osinfo;