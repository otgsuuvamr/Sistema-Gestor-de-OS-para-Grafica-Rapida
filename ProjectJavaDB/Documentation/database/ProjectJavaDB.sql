create database dbsyscontroll;
use dbsyscontroll;

create table userinfo(
iduser int primary key,
usr varchar(20) not null,
login varchar(20) not null unique,
pswrd varchar(20) not null,
perfil varchar(10) not null
);

insert into userinfo(iduser, usr, login, pswrd, perfil)
values(1,'ADMIN','admin','adm@2023!', 'admin');
insert into userinfo(iduser, usr, login, pswrd, perfil)
values(2, 'SUPORTE', 's', 'sup@2023!', 'admin');

create table clientes(
idcli int primary key auto_increment,
nomecli varchar(50) not null,
phonecli varchar(15) not null,
emailcli varchar(50)
);

create table osinfo(
os int primary key auto_increment,
data_os timestamp default current_timestamp,
idcli int not null,
foreign key(idcli) references clientes(idcli),
produto varchar(150) not null,
descricao varchar(150),
funcionario varchar(100) not null,
status varchar(30),
quantidade int not null,
valorttl decimal (10,2),
pagamento varchar(25)
);