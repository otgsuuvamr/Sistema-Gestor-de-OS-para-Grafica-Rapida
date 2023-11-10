drop database dbsyscontroll;
create database dbsyscontroll;
use dbsyscontroll;

create table userinfo(
iduser int primary key,
usr varchar(20) not null,
login varchar(20) not null unique,
pswrd varchar(20) not null,
perfil varchar(10) not null
);
describe userinfo;

insert into userinfo(iduser, usr, login, pswrd, perfil)
values(1,'ADMIN','admin','adm@2018!', 'admin');
insert into userinfo(iduser, usr, login, pswrd, perfil)
values(2,'GUSTAVO','rose','rose1234@abcd', 'admin');
insert into userinfo(iduser, usr, login, pswrd, perfil)
values(3, 'SUPORTE', 's', 'sup', 'admin');

select * from userinfo;

select * from userinfo where login='admin' and pswrd='adm@2018!';

create table clientes(
idcli int primary key auto_increment,
nomecli varchar(50) not null,
phonecli varchar(15) not null,
emailcli varchar(50)
);
describe clientes;
select * from clientes;

create table osinfo(
os int primary key auto_increment, -- Nº da OS
data_os timestamp default current_timestamp, -- data juntamente de OS
idcli int not null, -- id cliente para pesquisa
foreign key(idcli) references clientes(idcli),
-- iduser int not null, -- id user para campo funcionario
-- foreign key(iduser) references userinfo(iduser),
produto varchar(150) not null, -- nome do produto
descricao varchar(150) not null, -- descrição de serviço
funcionario varchar(100) not null, -- nome do funcionario
status varchar(30), -- instancia do produto
quantidade int not null, -- quantidade de material
valorttl decimal (10,2), -- valor total
pagamento varchar(25) -- método de pagamento
);
describe osinfo;
select * from osinfo;

select idcli as ID, nomecli as NOME, phonecli as FONE, emailcli as EMAIL from clientes;

insert into clientes(nomecli, phonecli, emailcli)
values('jenilson ribeiro', '19987345274', 'janilsonrb@gmail.com');
insert into clientes(nomecli, phonecli, emailcli)
values('flavio henrique', '92738293540', 'inflavio@gmail.com');
insert into clientes(nomecli, phonecli, emailcli)
values('maria julia', '19098234836', 'majucalu@gmail.com');
insert into clientes(nomecli, phonecli, emailcli)
values('lorenzo gabriel', '51983769204', 'lgw.deev@gmail.com');
insert into clientes(nomecli, phonecli, emailcli)
values('marli da silva', '7498341526', 'marlisilva@gmail.com');
insert into clientes(nomecli, phonecli, emailcli)
values('felipe araujo', '19999263541', 'faraujo@gmail.com');
insert into clientes(nomecli, phonecli, emailcli)
values('debora santos', '99987636514', 'debsantos@gmail.com');

select os, date_format(data_os, '%d/%m/%Y - %H:%i'), idcli, produto, descricao, funcionario, status, quantidade, valorttl, pagamento from osinfo where os = 1;

select * from clientes order by nomecli;

select OSER.os, data_os, produto, descricao, quantidade, valorttl, pagamento, CLI.nomecli, phonecli from osinfo as OSER inner join clientes as CLI on (CLI.idcli = OSER.idcli);

commit