-- Universidad de Los Andes
-- Sistemas Transaccionales
-- Grupo B-09
-- Escenarios de Pruebas


-- 1 Unicidad de tuplas PK

insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (555, 'Fraze', 'Pesterfield', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (555, 'New Fraze', 'New Pesterfield', 'empresa');

insert into HOTELES (id, REGISTRO_CAMARA_COMERCIO, REGISTRO_SUPERINTENDENCIA, TIPO_HABITACION, HORARIO_ADMIN_24H, TAMANO, UBICACION, CANTIDAD_NOCHES) values (555, 'Duis mi. Nulla ac enim.', 'estandar', 1, 'pequeño', '1984 Oneill Trail', 8);
insert into HOTELES (id, REGISTRO_CAMARA_COMERCIO, REGISTRO_SUPERINTENDENCIA, TIPO_HABITACION, HORARIO_ADMIN_24H, TAMANO, UBICACION, CANTIDAD_NOCHES) values (555, 'Duis mi. Nulla ac enim.', 'estandar', 1, 'pequeño', '1984 Oneill Trail', 8);

insert into HOSTELES (id, REGISTRO_CAMARA_COMERCIO, REGISTRO_SUPERINTENDENCIA, TIPO_HABITACION, TAMANO, UBICACION, CANTIDAD_NOCHES, HORARIO_ADMIN_INICIAL, HORARIO_ADMIN_FINAL) values (555, 'Intt.', 'compartida', 'mediano', '6 Erie Lane', 16, 5, 11);
insert into HOSTELES (id, REGISTRO_CAMARA_COMERCIO, REGISTRO_SUPERINTENDENCIA, TIPO_HABITACION, TAMANO, UBICACION, CANTIDAD_NOCHES, HORARIO_ADMIN_INICIAL, HORARIO_ADMIN_FINAL) values (555, 'Intt.', 'compartida', 'mediano', '6 Erie Lane', 16, 5, 11);

insert into VIVIENDAS_UNIVERSITARIAS (id, ubicacion, capacidad, menaje, tipo_habitacion, mensual, descripcion) values (555, '426 Cardinal Point', 'mediano', 'Quibulum.', 'compartida', 0, 'Inlorem.');
insert into VIVIENDAS_UNIVERSITARIAS (id, ubicacion, capacidad, menaje, tipo_habitacion, mensual, descripcion) values (555, '426 Cardinal Point', 'mediano', 'Quibulum.', 'compartida', 0, 'Inlorem.');

insert into APARTAMENTOS (id, amoblado, costo_admin) values (555, 1, 5726566.0); 
insert into APARTAMENTOS (id, amoblado, costo_admin) values (555, 1, 5726566.0); 

insert into HABITACIONES (id, precio_especial, tipo_habitacion) values (555, 0, 'semisuite'); 
insert into HABITACIONES (id, precio_especial, tipo_habitacion) values (555, 0, 'semisuite'); 

insert into SERVICIOS_BASICOS_HOTELEROS (ID, NOMBRE) values (555, 'sala_estudio');
insert into SERVICIOS_BASICOS_HOTELEROS (ID, NOMBRE) values (555, 'sala_estudio');

insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (555, 4, 'Duisst.', '8 Burning Wood Trail');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (555, 4, 'Duisst.', '8 Burning Wood Trail');

insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (555, 1, null, null, 1, null, 1, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (555, 1, null, null, 1, null, 1, null);

insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (555, 63, 63, '2016-04-16 12:49:56', null, '2018-01-31 09:05:59', 26, 326957.58);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (555, 63, 63, '2016-04-16 12:49:56', null, '2018-01-31 09:05:59', 26, 326957.58);







-- 2 integridad con FK
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 1, 1);

insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 1, 777);

DELETE FROM HOTELES WHERE ID = 1;






-- 3 restricciones de chequeo
insert into HOTELES (id, REGISTRO_CAMARA_COMERCIO, REGISTRO_SUPERINTENDENCIA, TIPO_HABITACION, HORARIO_ADMIN_24H, TAMANO, UBICACION, CANTIDAD_NOCHES) values (555, 'HOTEL VALIDO POR TIPO CHECK ESTANDAR.', 'estandar', 1, 'pequeño', '1984 Oneill Trail', 8);

insert into HOTELES (id, REGISTRO_CAMARA_COMERCIO, REGISTRO_SUPERINTENDENCIA, TIPO_HABITACION, HORARIO_ADMIN_24H, TAMANO, UBICACION, CANTIDAD_NOCHES) values (555, 'HOTEL NO VALIDO.', 'no sirve', 1, 'pequeño', '1984 Oneill Trail', 8);



