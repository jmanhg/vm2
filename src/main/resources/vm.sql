truncate table zigmapat.gacetadata_rasgos;

truncate table zigmapat.denominacion;

drop table zigmapat.gacetadata_rasgos;

drop table zigmapat.denominacion;

CREATE TABLE `denominacion_tmp` (
  `idregistro` mediumint(8) NOT NULL,
  `registro` varchar(50) DEFAULT NULL,
  `den` varchar(254) CHARACTER SET latin1 COLLATE latin1_spanish_ci DEFAULT NULL,
  `clase` varchar(254) NOT NULL,
  PRIMARY KEY (`idregistro`),
  UNIQUE KEY `id_UNIQUE` (`idregistro`),
  KEY `index3` (`den`),
  KEY `index4` (`registro`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `denominacion` (
  `idden` int(11) NOT NULL AUTO_INCREMENT,
  `den` varchar(255) COLLATE latin1_spanish_ci NOT NULL,
  `clase` varchar(255) COLLATE latin1_spanish_ci NOT NULL,
  PRIMARY KEY (`idden`)
) ENGINE=InnoDB AUTO_INCREMENT=1441771 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;



CREATE TABLE `gacetadata_rasgos` (
  `idden` int(11) NOT NULL DEFAULT '0',
  `cod_rasgo` varchar(8) CHARACTER SET latin1 COLLATE latin1_spanish_ci NOT NULL,
  `fac_peso` smallint(2) NOT NULL,
  `map_deno` varchar(100) DEFAULT NULL,
  KEY `index1` (`idden`),
  KEY `index2` (`cod_rasgo`),
  KEY `index3` (`map_deno`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE denominacion_tmp 
MODIFY clase VARCHAR(255) CHARACTER SET latin1 COLLATE latin1_spanish_ci;

update oposicion.gacetadata_g6
set gaceta='20092016SM',fecha_registro='2016/09/20';

select * from  oposicion.gacetadata_g6 where tipo_solicitud not in (1,2,3);

delete from  oposicion.gacetadata_g6 where expediente in (8284,8285) and titular like 'DENOMINACI%INTERNA%';

-- delete g.* from oposicion.gacetadata_g6 g6, vmpro.gacetadata g
-- where g6.expediente=g.solicitud;

insert into vmpro.gacetadata
select * from oposicion.gacetadata_g6;

insert into vmpro.gacetadata_viena
select * from oposicion.gacetadata_g6v;



update gacetadata
set tipo='SM'
where tipo='1';

update gacetadata
set tipo='SNC'
where tipo='2';

update gacetadata
set tipo='SAC'
where tipo='3';

select * from  gacetadata where tipo not in  ('M','MR','SM','SAC','SNC');

select * from  gacetadata where gaceta='20092016SM';

truncate table denominacion_tmp;

insert into denominacion_tmp 
select distinct idregistro, registro,titulo,clase 
from gacetadata 
where titulo is not null 
and titulo!='' 
and  clase is not null 
and tipo in ('M','MR','SM','SAC','SNC')
and gaceta='20092016SM';

ALTER TABLE denominacion_tmp 
MODIFY clase VARCHAR(255) CHARACTER SET latin1 COLLATE latin1_spanish_ci;

insert into denominacion (den,clase)
select distinct trim(den) den, trim(clase) clase from denominacion_tmp;



-- select idden,count(*) from denominacion d, denominacion_tmp dt where d.den=dt.den and d.clase=dt.clase
-- group by idden having count(*)>1;

-- create table gacetadata_den
insert into gacetadata_den
select idregistro,idden from denominacion d, denominacion_tmp dt where d.den=dt.den and d.clase=dt.clase
;

-- ejecutar fonetico

-- generar clases

SELECT clase,length(clase) FROM  gacetadata where clase is not null and gaceta='20092016SM' and length(clase)>2;

insert into gacetadata_clase
SELECT idregistro,solicitud,clase FROM  gacetadata where clase is not null and gaceta='20092016SM';

-- recuperar imagenes

select concat(0,'XX',solicitud) from gacetadata where gaceta='20092016SM';

select * from gacetadata where imagen is not null and gaceta='20092016SM' limit 10;


-- crear updates
create table gacetadata_update_20092016
select * from gacetadata where gaceta='20092016SM';


create table gacetadata_clase_update_20092016
select * from gacetadata_clase where idregistro in(
select idregistro from gacetadata where gaceta='20092016SM');


create table gacetadata_viena_update_20092016
select * from gacetadata_viena where solicitud in(
select solicitud from gacetadata where gaceta='20092016SM');

create table gacetadata_den_update_20092016
select * from gacetadata_den where idregistro in(
select idregistro from gacetadata where gaceta='20092016SM');

ALTER TABLE `gacetadata_den_update_20092016` 
ADD INDEX `index1` (`idden` ASC);

create table gacetadata_rasgos_update_20092016
select * from gacetadata_rasgos where idden in (
select idden from gacetadata_den_update_20092016);


-- mysqldump -u root -p vmpro gacetadata_update_20092016 gacetadata_clase_update_20092016 gacetadata_viena_update_20092016 gacetadata_den_update_20092016 gacetadata_rasgos_update_20092016 > c:\juan\vmpro_update_20092016.sql


-- update TXT

/*

insert into gacetadata
select * from gacetadata_update_20092016;

insert into gacetadata_clase
select * from gacetadata_clase_update_20092016 ;

insert into gacetadata_viena
select * from gacetadata_viena_update_20092016 ;

insert into gacetadata_den
select * from gacetadata_den_update_20092016 ;

insert into gacetadata_rasgos
select * from gacetadata_rasgos_update_20092016;
*/

select concat(solicitud,',') from gacetadata where gaceta='13092016SM';




-- HELP

SELECT count(*) FROM  denominacion d left join gacetadata_rasgos r on d.idden=r.idden 
where  r.idden is null ;

-- create table denominacion_borrados
insert into denominacion_borrados
SELECT d.* FROM  denominacion d left join gacetadata_rasgos r on d.idden=r.idden 
where  r.idden is null ;

delete d.* FROM  denominacion d left join gacetadata_rasgos r on d.idden=r.idden 
where  r.idden is null ;

-- 

select den,count(*) from denominacion group by den order by count(*) desc;

select den, trim(replace(group_concat(clase separator ' '),'  ',' ')) clase 
  from denominacion_tmp2
  -- where den like '%COCA%' and den is not null and clase is not null
  group by den;

select * from gacetadata g, gacetadata_clase c where g.idregistro=c.idregistro and g.titulo is not null 
and g.titulo!='' 
and  g.clase is not null 
and g.tipo in ('M','MR','SM') and c.clase>60;

select * from denominacion where idden in (
select d.idden from denominacion d , gacetadata_den g, gacetadata_clase c where d.idden=g.idden and g.idregistro=c.idregistro and c.clase>60)
;

select * from denominacion where clase like '%1419%' ;
 
 
 select * from gacetadata where clase like '%1419%'  ;
 
 
 
select titulo,mid(titulo,2,length(titulo)-2) from gacetadata where titulo like '"%' and titulo like '%"';



update gacetadata set titulo=mid(titulo,2,length(titulo)-2)  where titulo like '"%' and titulo like '%"';
update denominacion_tmp2 set den=mid(den,2,length(den)-2)  where den like '"%' and den like '%"';

select titulo,mid(titulo,1,length(titulo)-1) from gacetadata where titulo like '%"';

select * from gacetadata_clase where clase>50;

 insert into denominacion_borrados
select * from denominacion where idden in (1413720);

delete from denominacion where idden in (1413720);

select * from denominacion ;

 insert into denominacion_borrados
select * from denominacion where length(den)>119 ;

select length(den),den from denominacion where idden=949317 ;

select * from denominacion where idden in (615858,37851);

select * from gacetadata_rasgos where idden in (615858,37851);

select count(*) from gacetadata_rasgos;

-- 

CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `fonetico` AS select `gv`.`solicitud` AS `solicitud`,`vv`.`solicitud` AS `solicitudv`,count(0) AS `numF` from (`gacetadatav_viena` `vv` join `gacetadata_viena` `gv`) where ((`vv`.`categoria` = `gv`.`categoria`) and (`vv`.`division` = `gv`.`division`) and (`vv`.`seccion` = `gv`.`seccion`)) group by `gv`.`solicitud`,`vv`.`solicitud`;

CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `perfiles_por_usuario` AS (select `usuario`.`id` AS `id`,`usuario`.`usuario` AS `usuario`,`perfil`.`codigo_perfil` AS `codigo_perfil` from ((`usuario` join `perfil`) join `usuario_perfil`) where ((`usuario`.`id` = `usuario_perfil`.`id_usuario`) and (`perfil`.`id` = `usuario_perfil`.`id_perfil`)) order by `usuario`.`id`);



