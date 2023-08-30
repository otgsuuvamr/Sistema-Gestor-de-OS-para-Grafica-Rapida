-- exclui a database para reiniciar com todas as ultimas alterações.
drop database dbsyscontroll;
-- cria a database.
create database dbsyscontroll;
-- seleciona a database a ser alterada/manipulada.
use dbsyscontroll;
-- cria tabelas na database pré-selecionada.
create table userinfo(
iduser int primary key,
usr varchar(20) not null,
phoneusr varchar(15),
login varchar(20) not null unique,
pswrd varchar(20) not null
);
create table clientes(
idcli int primary key auto_increment,
nomecli varchar(50) not null,
phonecli varchar(15) not null
);
-- descreve a tabela especificada.
describe userinfo;
-- insere dados em determinada tabela.
insert into userinfo(iduser, usr, phoneusr, login, pswrd)
values(1,'rose','1998-3032','iamnotrose','gmr220181');
-- exibe os dados da tabela.
select * from userinfo;

insert into userinfo(iduser, usr, phoneusr, login, pswrd)
values(2,'admin','2348-3046','admin','adm220181');
insert into userinfo(iduser, usr, phoneusr, login, pswrd)
values(3,'suporte','3658-3685','suporte','sup220181');
-- sobrepoe as informações em determinado campo da tabela.
update userinfo set phoneusr='1278-9128' where iduser=2;
-- deleta informações da tabela.
delete from userinfo where iduser=3;