

-- 
-- 
-- CREAR
-- 
-- 


-- crea la tabla PERSONAS
CREATE TABLE PERSONAS (
    ID INT, 
    NOMBRE VARCHAR(25), 
    APELLIDO VARCHAR(25), 
    TIPO VARCHAR(25) 
);

-- crea la tabla RESERVAS
CREATE TABLE RESERVAS ( 
    ID INT, 
    ID_PERSONA INT, 
    ID_PROPUESTA INT,
    FECHA_REGISTRO VARCHAR(25), 
    FECHA_CANCELACION VARCHAR(25), 
    FECHA_INICIO_ESTADIA VARCHAR(25), 
    DURACION_CONTRATO INT, 
    COSTO_TOTAL DOUBLE
);

-- crea la tabla PROPUESTAS
create table PROPUESTAS (
	ID INT,
    ID_PERSONA INT,
	ID_HOTEL INT,
	ID_HOSTEL INT,
	ID_VIVIENDA_EXPRESS INT,
	ID_APARTAMENTO INT,
	ID_VIVIENDA_UNIVERSITARIA INT,
	ID_HABITACION INT
);

-- crea la tabla HOTELES
CREATE TABLE HOTELES ( 
    ID INT, 
    REGISTRO_CAMARA_COMERCIO VARCHAR(500), 
    REGISTRO_SUPERINTENDENCIA VARCHAR(500), 
    TIPO_HABITACION VARCHAR(25), 
    HORARIO_ADMIN_24H NUMBER(1,0), 
    TAMANO VARCHAR(25), 
    UBICACION VARCHAR(50), 
    CANTIDAD_NOCHES INT 
);

-- crea la tabla HOSTELES
CREATE TABLE HOSTELES ( 
    ID INT, 
    REGISTRO_CAMARA_COMERCIO VARCHAR(500), 
    REGISTRO_SUPERINTENDENCIA VARCHAR(500),
    TIPO_HABITACION VARCHAR(25), 
    HORARIO_ADMIN_INICIAL INT, 
    HORARIO_ADMIN_FINAL INT, 
    TAMANO VARCHAR(25), 
    UBICACION VARCHAR(50), 
    CANTIDAD_NOCHES INT 
);


-- crea tabla HOTEL_TIENE_SERVICIOS
CREATE TABLE HOTEL_TIENE_SERVICIOS (
    ID_SERVICIO_BASICO_HOTELERO INT,
    ID_HOTEL INT, 
    ID_HOSTEL INT 
);

-- crea tabla SERVICIOS_BASICOS_HOTELEROS
CREATE TABLE SERVICIOS_BASICOS_HOTELEROS ( 
    ID INT, 
    NOMBRE VARCHAR(25)
);

-- crea tabla VIVIENDA_EXPRESS
CREATE TABLE VIVIENDAS_EXPRESS ( 
    ID INT, 
    NUMERO_HABITACIONES INT, 
    MENAJE VARCHAR(200) , 
    UBICACION VARCHAR(50) 
);

-- crea tabla APARTAMENTO
CREATE TABLE APARTAMENTOS ( 
    ID INT, 
    AMOBLADO NUMBER(1,0), 
    COSTO_ADMIN FLOAT 
);

-- crea tabla VIVIENDA_UNIVERITARIA
CREATE TABLE VIVIENDAS_UNIVERSITARIAS ( 
    ID INT, 
    UBICACION VARCHAR(50), 
    CAPACIDAD VARCHAR(25), 
    MENAJE VARCHAR(200), 
    TIPO_HABITACION VARCHAR(25), 
    MENSUAL NUMBER(1,0), 
    DESCRIPCION VARCHAR(200) 
);

-- crea tabla HABITACION 
CREATE TABLE HABITACIONES ( 
    ID INT, 
    PRECIO_ESPECIAL NUMBER(1,0), 
    TIPO_HABITACION VARCHAR(25) 
);

-- Crea tabla SERVICIOS_BASICOS
CREATE TABLE SERVICIOS_BASICOS ( 
    ID INT, 
    ID_TIPO INT, 
    ID_HABITACION INT, 
    ID_VIVIENDA_UNIVERSITARIA INT, 
    ID_APARTAMENTO INT, 
    ID_VIVIENDA_EXPRESS INT 
);

-- crea tabla TIPOS de servicio basico
CREATE TABLE TIPOS ( 
    ID INT, 
    NOMBRE VARCHAR(25), 
    COSTO DOUBLE 
);









-- 
-- 
-- ALTERAR
-- 
-- 


-- PERSONAS
ALTER TABLE PERSONAS ADD PRIMARY KEY (ID);
ALTER TABLE PERSONAS MODIFY NOMBRE VARCHAR(25) NOT NULL;
ALTER TABLE PERSONAS MODIFY APELLIDO VARCHAR(25) NOT NULL;
ALTER TABLE PERSONAS MODIFY TIPO VARCHAR(25) DEFAULT 'normal';


-- HOTELES
ALTER TABLE HOTELES ADD PRIMARY KEY (ID);
ALTER TABLE HOTELES MODIFY REGISTRO_CAMARA_COMERCIO NOT NULL;
ALTER TABLE HOTELES MODIFY REGISTRO_SUPERINTENDENCIA NOT NULL;
ALTER TABLE HOTELES  add constraint TIPOS check (TIPO_HABITACION in ('estandar', 'semisuite', 'suite'));
ALTER TABLE HOTELES add constraint TAMANOS check (TAMANO in ('grande', 'mediano', 'pequeño'));

-- HOTELES
ALTER TABLE HOSTELES ADD PRIMARY KEY (ID);
ALTER TABLE HOSTELES MODIFY REGISTRO_CAMARA_COMERCIO TEXT NOT NULL;
ALTER TABLE HOSTELES MODIFY REGISTRO_SUPERINTENDENCIA TEXT NOT NULL;
ALTER TABLE HOSTELES  add constraint HORARIO_I check (HORARIO_ADMIN_INICIAL in (0, 24));
ALTER TABLE HOSTELES  add constraint HORARIO_F check (HORARIO_ADMIN_FINAL in (0, 24));
ALTER TABLE HOSTELES  add constraint TIPO_HAB check (TIPO_HABITACION in ('compartida', 'sencilla'));

-- SERVICIOS_BASICOS_HOTELEROS
ALTER TABLE SERVICIOS_BASICOS_HOTELEROS ADD PRIMARY KEY (ID);

-- HOTEL_TIENE_SERVICIOS
ALTER TABLE HOTEL_TIENE_SERVICIOS ADD FOREIGN KEY (ID_HOTEL) REFERENCES HOTELES(ID);
ALTER TABLE HOTEL_TIENE_SERVICIOS ADD FOREIGN KEY (ID_HOSTEL) REFERENCES HOSTELES(ID);
ALTER TABLE HOTEL_TIENE_SERVICIOS ADD FOREIGN KEY (ID_SERVICIO_BASICO_HOTELERO) REFERENCES SERVICIOS_BASICOS_HOTELEROS(ID);

-- VIVIENDA_EXPRESS
ALTER TABLE VIVIENDAS_EXPRESS ADD PRIMARY KEY (ID);

-- APARTAMENTO
ALTER TABLE APARTAMENTOS ADD PRIMARY KEY (ID);

-- VIVIENDA_UNIVERSITARIA
ALTER TABLE VIVIENDAS_UNIVERSITARIAS ADD PRIMARY KEY (ID);

-- HABIACION 
ALTER TABLE HABITACIONES ADD PRIMARY KEY (ID);

-- TIPO
ALTER TABLE TIPOS ADD PRIMARY KEY (ID);
ALTER TABLE TIPOS MODIFY COSTO DEFAULT NULL;

-- SERVICIOS_BASICOS
ALTER TABLE SERVICIOS_BASICOS ADD PRIMARY KEY (ID);
ALTER TABLE SERVICIOS_BASICOS ADD FOREIGN KEY (ID_TIPO) REFERENCES TIPOS (ID);
ALTER TABLE SERVICIOS_BASICOS ADD FOREIGN KEY (ID_HABITACION) REFERENCES HABITACIONES (ID);
ALTER TABLE SERVICIOS_BASICOS ADD FOREIGN KEY (ID_VIVIENDA_UNIVERSITARIA) REFERENCES VIVIENDAS_UNIVERSITARIAS (ID);
ALTER TABLE SERVICIOS_BASICOS ADD FOREIGN KEY (ID_APARTAMENTO) REFERENCES APARTAMENTOS (ID);
ALTER TABLE SERVICIOS_BASICOS ADD FOREIGN KEY (ID_VIVIENDA_EXPRESS) REFERENCES VIVIENDAS_EXPRESS (ID);

-- PROPUESTAS
ALTER TABLE PROPUESTAS ADD PRIMARY KEY (ID);
ALTER TABLE PROPUESTAS ADD FOREIGN KEY (ID_PERSONA) REFERENCES PERSONAS (ID);
ALTER TABLE PROPUESTAS ADD FOREIGN KEY (ID_HOTEL) REFERENCES HOTELES (ID);
ALTER TABLE PROPUESTAS ADD FOREIGN KEY (ID_HOSTEL) REFERENCES HOSTELES (ID);
ALTER TABLE PROPUESTAS ADD FOREIGN KEY (ID_VIVIENDA_EXPRESS) REFERENCES VIVIENDAS_EXPRESS (ID);
ALTER TABLE PROPUESTAS ADD FOREIGN KEY (ID_APARTAMENTO) REFERENCES APARTAMENTOS (ID);
ALTER TABLE PROPUESTAS ADD FOREIGN KEY (ID_VIVIENDA_UNIVERSITARIA) REFERENCES VIVIENDAS_UNIVERSITARIAS (ID);
ALTER TABLE PROPUESTAS ADD FOREIGN KEY (ID_HABITACION) REFERENCES HABITACIONES (ID);

-- RESERVAS
ALTER TABLE RESERVAS ADD PRIMARY KEY (ID);
ALTER TABLE RESERVAS ADD FOREIGN KEY (ID_PERSONA) REFERENCES PERSONAS (ID);
ALTER TABLE RESERVAS MODIFY FECHA_CANCELACION VARCHAR(25) DEFAULT NULL;
ALTER TABLE RESERVAS ADD FOREIGN KEY (ID_PROPUESTA) REFERENCES PROPUESTAS (ID);












-- 
-- 
-- INSERT DATA
-- 
-- 






-- PERSONAS
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (1, 'Fraze', 'Pesterfield', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (2, 'Chris', 'Shelper', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (3, 'Darius', 'Maylor', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (4, 'Christian', 'Worboy', 'registrado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (5, 'Alejandrina', 'Forty', 'empleado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (6, 'Ellette', 'Swinfen', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (7, 'Caroline', 'Gull', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (8, 'Kiele', 'Isakovic', 'empleado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (9, 'Constantia', 'Sciusscietto', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (10, 'Margaux', 'Sawbridge', 'empleado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (11, 'Perkin', 'Ragborne', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (12, 'Ingmar', 'Bertrand', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (13, 'Prudi', 'Martinets', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (14, 'Glennis', 'Dwane', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (15, 'Ibby', 'Cleve', 'registrado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (16, 'Lacee', 'Sango', 'empleado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (17, 'Sheelagh', 'Riccardini', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (18, 'Gipsy', 'McArthur', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (19, 'Michal', 'Sodo', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (20, 'Darda', 'Baily', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (21, 'Ynez', 'Marden', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (22, 'Miltie', 'Teresa', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (23, 'Dorise', 'Edbrooke', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (24, 'Dulciana', 'Skyner', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (25, 'Izak', 'Worsham', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (26, 'Alicia', 'Tooting', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (27, 'Marylinda', 'Carthew', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (28, 'Franklyn', 'Redmond', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (29, 'Cacilia', 'Dodgshon', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (30, 'Forbes', 'Winchcomb', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (31, 'Ginelle', 'Kender', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (32, 'Alyssa', 'Forseith', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (33, 'Doralynne', 'Vasnetsov', 'estudiante');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (34, 'Neddie', 'Yakebowitch', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (35, 'Anjela', 'Handman', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (36, 'Mindy', 'Atte-Stone', 'registrado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (37, 'Mariel', 'Clinnick', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (38, 'Brucie', 'Bramstom', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (39, 'Pattin', 'Santry', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (40, 'Grady', 'Pulley', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (41, 'Cathrin', 'Sinkin', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (42, 'Jennee', 'Suthworth', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (43, 'Lindsy', 'Bown', 'empleado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (44, 'Andreana', 'Sowray', 'empleado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (45, 'Freda', 'Panther', 'empleado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (46, 'Edythe', 'Potter', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (47, 'Dev', 'Kintzel', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (48, 'Marcos', 'Heaford', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (49, 'Josephina', 'Wayte', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (50, 'Piggy', 'Birkenshaw', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (51, 'Adrianna', 'Friskey', 'empleado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (52, 'Matty', 'Kenington', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (53, 'Wilone', 'Vautier', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (54, 'Lew', 'Ulyatt', 'registrado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (55, 'Ulick', 'Ditch', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (56, 'Sharona', 'Hrishanok', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (57, 'Clem', 'O''Crigane', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (58, 'Kynthia', 'Ravenscroft', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (59, 'Denny', 'McKiernan', 'empleado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (60, 'Viva', 'Werrit', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (61, 'Briny', 'Daily', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (62, 'Heidie', 'Creswell', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (63, 'Roxane', 'Speak', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (64, 'Louisette', 'Fowkes', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (65, 'Ondrea', 'Moran', 'empleado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (66, 'Shantee', 'Matyushonok', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (67, 'Linnea', 'Castelletti', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (68, 'Lorelei', 'Gosnoll', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (69, 'Sibelle', 'Ashbee', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (70, 'Orrin', 'Fassman', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (71, 'Lula', 'Hasted', 'registrado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (72, 'Valdemar', 'Keyte', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (73, 'Joelynn', 'Raisher', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (74, 'Brianne', 'Chalk', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (75, 'Calvin', 'Ficken', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (76, 'Costa', 'Newham', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (77, 'Nissy', 'Pardi', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (78, 'Carling', 'Nieass', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (79, 'Tamarah', 'Millam', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (80, 'Duncan', 'Stodd', 'empleado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (81, 'Ches', 'Tirone', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (82, 'Hallie', 'Netley', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (83, 'Kiley', 'Azema', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (84, 'Ogden', 'Evett', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (85, 'Cyrillus', 'Lightollers', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (86, 'Rufus', 'Butt', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (87, 'Margaret', 'Bonniface', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (88, 'Leeann', 'Keech', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (89, 'Trent', 'Rostron', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (90, 'Matias', 'Pahlsson', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (91, 'Cloe', 'Ley', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (92, 'Gertrud', 'Dahle', 'padre');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (93, 'Lacy', 'Casin', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (94, 'Granville', 'Ensor', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (95, 'Giustina', 'Lockie', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (96, 'Tarah', 'Thaw', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (97, 'Melloney', 'Burgett', 'registrado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (98, 'Royce', 'Gadsdon', 'empresa');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (99, 'Edy', 'Scranny', 'invitado');
insert into PERSONAS (ID, NOMBRE, APELLIDO, TIPO) values (100, 'Sorcha', 'Whaley', 'padre');



-- HOTELES

-- HOSTELES


-- VIVIENDAS_UNIVERSITARIAS

-- APARTAMENTOS

-- HABITACIONES










-- HOTEL_TIENE_SERVICIO
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 1, 1);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 2, 2);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 3, 3);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 4, 4);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 5, 5);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 6, 6);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 7, 7);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 8, 8);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 9, 9);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 10, 10);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 11, 11);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 12, 12);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 13, 13);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 14, 14);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 15, 15);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 16, 16);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 17, 17);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 18, 18);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 19, 19);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 20, 20);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 21, 21);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 22, 22);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 23, 23);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 24, 24);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 25, 25);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 26, 26);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 27, 27);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 28, 28);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 29, 29);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 30, 30);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 31, 31);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 32, 32);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 33, 33);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 34, 34);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 35, 35);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 36, 36);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 37, 37);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 38, 38);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 39, 39);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 40, 40);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 41, 41);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 42, 42);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 43, 43);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 44, 44);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 45, 45);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 46, 46);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 47, 47);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 48, 48);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 49, 49);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 50, 50);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 51, 51);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 52, 52);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 53, 53);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 54, 54);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 55, 55);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 56, 56);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 57, 57);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 58, 58);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 59, 59);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 60, 60);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 61, 61);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 62, 62);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 63, 63);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 64, 64);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 65, 65);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 66, 66);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 67, 67);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 68, 68);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 69, 69);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 70, 70);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 71, 71);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 72, 72);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 73, 73);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 74, 74);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 75, 75);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 76, 76);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 77, 77);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 78, 78);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 79, 79);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 80, 80);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 81, 81);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 82, 82);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 83, 83);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (6, 84, 84);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 85, 85);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 86, 86);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 87, 87);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 88, 88);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 89, 89);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 90, 90);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 91, 91);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 92, 92);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 93, 93);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 94, 94);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 95, 95);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (1, 96, 96);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (3, 97, 97);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (4, 98, 98);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (2, 99, 99);
insert into HOTEL_TIENE_SERVICIOS (ID_SERVICIO_BASICO_HOTELERO, ID_HOTEL, ID_HOSTEL) values (5, 100, 100);









-- SERVICIOS_BASICOS_HOTELEROS
insert into SERVICIOS_BASICOS_HOTELEROS (ID, NOMBRE) values (3, 'sala_estudio');
insert into SERVICIOS_BASICOS_HOTELEROS (ID, NOMBRE) values (4, 'spa');
insert into SERVICIOS_BASICOS_HOTELEROS (ID, NOMBRE) values (6, 'gym');
insert into SERVICIOS_BASICOS_HOTELEROS (ID, NOMBRE) values (6, 'restaurante');
insert into SERVICIOS_BASICOS_HOTELEROS (ID, NOMBRE) values (6, 'sala');
insert into SERVICIOS_BASICOS_HOTELEROS (ID, NOMBRE) values (6, 'jacuzzi');






-- VIVIENDA_EXPRESS
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (1, 4, 'Duis at velit eu est congue elementum. In hac habitasse platea dictumst.', '8 Burning Wood Trail');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (2, 2, 'Donec ut dolor.', '9223 Eggendart Plaza');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (3, 5, 'Nunc purus. Phasellus in felis.', '59262 Coleman Lane');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (4, 1, 'Pellentesque at nulla.', '59 Eagle Crest Plaza');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (5, 2, 'Nullam molestie nibh in lectus.', '8576 Laurel Plaza');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (6, 1, 'Sed vel enim sit amet nunc viverra dapibus.', '7 Steensland Avenue');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (7, 3, 'Aenean fermentum.', '883 Cordelia Crossing');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (8, 2, 'Quisque ut erat.', '99433 Independence Center');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (9, 5, 'Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula.', '9606 Chive Way');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (10, 1, 'Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam.', '437 Heath Lane');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (11, 2, 'Curabitur at ipsum ac tellus semper interdum.', '734 Rockefeller Pass');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (12, 5, 'Sed sagittis. Nam congue, risus semper porta volutpat, quam pede lobortis ligula, sit amet eleifend pede libero quis orci.', '0162 Summerview Trail');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (13, 3, 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi.', '4642 Little Fleur Road');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (14, 1, 'Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi.', '75195 Shoshone Lane');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (15, 1, 'Maecenas ut massa quis augue luctus tincidunt.', '0653 Milwaukee Crossing');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (16, 4, 'Sed sagittis.', '6767 Ramsey Circle');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (17, 5, 'Integer tincidunt ante vel ipsum.', '3609 Kedzie Point');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (18, 4, 'Curabitur gravida nisi at nibh.', '78 Monument Circle');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (19, 3, 'Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue.', '65 Goodland Drive');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (20, 1, 'In congue. Etiam justo.', '035 Cottonwood Terrace');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (21, 5, 'Aliquam non mauris.', '53947 Independence Lane');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (22, 1, 'Etiam pretium iaculis justo. In hac habitasse platea dictumst.', '22067 Annamark Center');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (23, 5, 'Nullam varius.', '4047 Mosinee Center');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (24, 5, 'Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor.', '58996 Forest Run Circle');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (25, 5, 'Nulla tellus. In sagittis dui vel nisl.', '0095 Cody Road');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (26, 2, 'Proin leo odio, porttitor id, consequat in, consequat ut, nulla.', '0 Tomscot Pass');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (27, 2, 'Vestibulum rutrum rutrum neque.', '45 Del Sol Hill');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (28, 4, 'Mauris lacinia sapien quis libero.', '245 Lakeland Crossing');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (29, 3, 'Morbi porttitor lorem id ligula.', '46 Sundown Plaza');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (30, 3, 'Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.', '66494 Magdeline Street');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (31, 3, 'Maecenas pulvinar lobortis est.', '761 Butterfield Point');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (32, 2, 'Maecenas ut massa quis augue luctus tincidunt.', '032 Hudson Hill');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (33, 3, 'In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.', '9 Scott Street');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (34, 2, 'Nunc nisl.', '057 Bonner Crossing');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (35, 5, 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam.', '77 Menomonie Street');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (36, 4, 'Nam dui. Proin leo odio, porttitor id, consequat in, consequat ut, nulla.', '14 Merry Place');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (37, 4, 'Proin at turpis a pede posuere nonummy.', '94840 Cascade Circle');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (38, 4, 'Nulla tempus.', '97 Mitchell Junction');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (39, 3, 'Nunc rhoncus dui vel sem. Sed sagittis.', '09 Linden Court');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (40, 4, 'Etiam vel augue.', '9 Cascade Park');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (41, 5, 'Donec vitae nisi.', '7146 Roxbury Park');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (42, 1, 'In hac habitasse platea dictumst. Maecenas ut massa quis augue luctus tincidunt.', '0 Becker Road');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (43, 3, 'Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque.', '1660 Hoffman Avenue');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (44, 2, 'Maecenas pulvinar lobortis est. Phasellus sit amet erat.', '12 Anniversary Court');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (45, 5, 'Vivamus vel nulla eget eros elementum pellentesque.', '1 Almo Way');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (46, 2, 'Etiam faucibus cursus urna.', '924 Tony Place');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (47, 5, 'Integer ac neque.', '6520 Maple Crossing');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (48, 1, 'Aliquam non mauris.', '5692 Del Mar Court');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (49, 3, 'Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo.', '425 Sundown Trail');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (50, 1, 'Suspendisse potenti.', '51464 Myrtle Avenue');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (51, 2, 'Curabitur gravida nisi at nibh.', '0 Coleman Court');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (52, 4, 'Nulla suscipit ligula in lacus.', '3939 Nancy Point');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (53, 1, 'Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.', '6 Eliot Junction');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (54, 1, 'Aliquam erat volutpat.', '2228 Valley Edge Drive');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (55, 4, 'Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis.', '936 Monument Alley');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (56, 1, 'Integer ac neque. Duis bibendum.', '0051 La Follette Circle');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (57, 5, 'Vivamus tortor. Duis mattis egestas metus.', '0 Tennyson Street');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (58, 5, 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est.', '05 Northridge Place');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (59, 3, 'Pellentesque ultrices mattis odio. Donec vitae nisi.', '94 Wayridge Parkway');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (60, 1, 'Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.', '8570 Vahlen Point');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (61, 1, 'Proin eu mi.', '90 Anderson Crossing');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (62, 1, 'Proin at turpis a pede posuere nonummy.', '13 Browning Trail');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (63, 5, 'Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus. Suspendisse potenti.', '8785 Village Green Pass');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (64, 1, 'Nam dui. Proin leo odio, porttitor id, consequat in, consequat ut, nulla.', '9 Johnson Circle');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (65, 3, 'Fusce consequat.', '7 New Castle Center');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (66, 5, 'Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.', '0592 Pierstorff Pass');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (67, 4, 'Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum.', '2308 Twin Pines Pass');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (68, 5, 'Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla.', '0 Autumn Leaf Way');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (69, 5, 'Aliquam erat volutpat.', '19074 Green Place');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (70, 5, 'Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla.', '10097 Sutherland Terrace');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (71, 1, 'Sed vel enim sit amet nunc viverra dapibus.', '7172 Blackbird Crossing');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (72, 1, 'Nunc rhoncus dui vel sem.', '77846 Packers Parkway');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (73, 1, 'Vestibulum ac est lacinia nisi venenatis tristique.', '62797 Walton Trail');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (74, 3, 'Vivamus in felis eu sapien cursus vestibulum.', '3 Hauk Circle');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (75, 1, 'Vestibulum sed magna at nunc commodo placerat.', '594 Sunfield Junction');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (76, 4, 'In congue. Etiam justo.', '4 Havey Lane');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (77, 2, 'Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat.', '50 Briar Crest Park');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (78, 2, 'In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.', '18819 Ohio Center');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (79, 2, 'Maecenas tincidunt lacus at velit.', '26 Bartelt Junction');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (80, 5, 'Phasellus id sapien in sapien iaculis congue.', '1 Darwin Crossing');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (81, 2, 'Morbi a ipsum. Integer a nibh.', '44290 Bartelt Avenue');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (82, 2, 'Praesent id massa id nisl venenatis lacinia.', '80 Erie Crossing');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (83, 5, 'Cras pellentesque volutpat dui. Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc.', '3367 Barnett Center');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (84, 2, 'Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.', '81 Fisk Crossing');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (85, 4, 'Curabitur convallis. Duis consequat dui nec nisi volutpat eleifend.', '3343 Chinook Avenue');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (86, 4, 'Phasellus id sapien in sapien iaculis congue.', '527 Arrowood Trail');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (87, 1, 'Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis.', '4603 Maple Alley');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (88, 2, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin interdum mauris non ligula pellentesque ultrices.', '9 Vahlen Pass');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (89, 2, 'In sagittis dui vel nisl.', '8469 Vidon Way');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (90, 1, 'Donec ut mauris eget massa tempor convallis.', '7 Loftsgordon Point');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (91, 4, 'Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus. Suspendisse potenti.', '820 Northview Pass');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (92, 4, 'In sagittis dui vel nisl.', '22 Acker Crossing');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (93, 1, 'Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus.', '8560 Bunting Terrace');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (94, 2, 'Phasellus in felis.', '418 Crowley Pass');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (95, 3, 'Proin interdum mauris non ligula pellentesque ultrices.', '519 Fairview Plaza');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (96, 2, 'Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla.', '90000 Vahlen Place');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (97, 2, 'Curabitur gravida nisi at nibh.', '46172 Farragut Drive');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (98, 5, 'Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.', '164 American Ash Circle');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (99, 2, 'In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.', '6 International Court');
insert into VIVIENDAS_EXPRESS (ID, NUMERO_HABITACIONES, MENAJE, UBICACION) values (100, 3, 'Aliquam sit amet diam in magna bibendum imperdiet.', '97494 Schlimgen Terrace');












-- PROPUESTAS
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (1, 1, null, null, 1, null, 1, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (2, 2, null, null, 2, null, 2, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (3, 3, null, null, 3, null, 3, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (4, 4, null, null, 4, null, 4, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (5, 5, null, null, 5, null, 5, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (6, 6, null, null, null, null, null, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (7, 7, null, null, 7, null, 7, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (8, 8, null, null, 8, 8, 8, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (9, 9, null, null, 9, null, null, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (10, 10, null, null, 10, null, 10, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (11, 11, null, null, 11, null, 11, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (12, 12, null, null, null, null, 12, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (13, 13, 13, 13, 13, null, 13, 13);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (14, 14, null, null, 14, null, 14, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (15, 15, null, null, 15, null, 15, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (16, 16, 16, 16, null, null, 16, 16);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (17, 17, 17, 17, 17, null, 17, 17);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (18, 18, null, null, 18, 18, 18, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (19, 19, null, null, 19, 19, 19, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (20, 20, null, null, 20, 20, 20, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (21, 21, null, null, null, null, 21, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (22, 22, null, null, null, null, 22, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (23, 23, null, null, 23, null, 23, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (24, 24, null, null, 24, null, 24, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (25, 25, null, null, 25, null, 25, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (26, 26, null, null, 26, null, 26, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (27, 27, null, null, 27, 27, 27, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (28, 28, null, null, 28, null, 28, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (29, 29, null, null, null, 29, 29, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (30, 30, 30, 30, 30, null, 30, 30);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (31, 31, null, null, 31, null, 31, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (32, 32, null, null, 32, null, 32, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (33, 33, null, null, 33, null, 33, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (34, 34, null, null, 34, null, 34, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (35, 35, null, null, 35, null, 35, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (36, 36, null, null, 36, null, 36, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (37, 37, null, null, 37, null, 37, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (38, 38, null, null, 38, null, 38, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (39, 39, 39, 39, null, 39, 39, 39);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (40, 40, null, null, 40, 40, 40, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (41, 41, null, null, null, null, 41, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (42, 42, null, null, 42, null, 42, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (43, 43, null, null, 43, 43, 43, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (44, 44, null, null, 44, 44, 44, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (45, 45, null, null, 45, null, 45, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (46, 46, null, null, 46, 46, 46, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (47, 47, null, null, 47, 47, 47, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (48, 48, null, null, null, null, 48, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (49, 49, null, null, 49, 49, 49, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (50, 50, null, null, 50, null, 50, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (51, 51, null, null, 51, null, 51, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (52, 52, null, null, 52, 52, 52, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (53, 53, null, null, 53, 53, 53, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (54, 54, 54, 54, 54, 54, 54, 54);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (55, 55, null, null, 55, null, null, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (56, 56, null, null, 56, null, 56, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (57, 57, null, null, 57, null, 57, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (58, 58, null, null, 58, null, 58, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (59, 59, null, null, 59, null, 59, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (60, 60, null, null, 60, null, 60, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (61, 61, null, null, 61, 61, 61, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (62, 62, null, null, 62, 62, 62, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (63, 63, null, null, null, null, 63, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (64, 64, null, null, 64, null, 64, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (65, 65, null, null, 65, 65, 65, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (66, 66, null, null, 66, null, 66, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (67, 67, null, null, 67, 67, 67, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (68, 68, null, null, 68, 68, 68, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (69, 69, null, null, 69, 69, 69, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (70, 70, null, null, 70, 70, 70, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (71, 71, null, null, 71, 71, 71, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (72, 72, 72, 72, 72, null, 72, 72);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (73, 73, null, null, 73, null, 73, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (74, 74, 74, 74, 74, null, 74, 74);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (75, 75, null, null, 75, null, 75, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (76, 76, null, null, 76, 76, 76, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (77, 77, 77, 77, 77, null, 77, 77);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (78, 78, null, null, 78, null, 78, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (79, 79, 79, 79, 79, 79, 79, 79);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (80, 80, 80, 80, 80, null, 80, 80);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (81, 81, null, null, 81, null, 81, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (82, 82, null, null, 82, 82, 82, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (83, 83, null, null, 83, null, null, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (84, 84, null, null, null, 84, null, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (85, 85, 85, 85, 85, null, 85, 85);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (86, 86, null, null, 86, 86, 86, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (87, 87, null, null, null, 87, 87, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (88, 88, null, null, 88, null, 88, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (89, 89, null, null, 89, null, 89, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (90, 90, null, null, 90, 90, 90, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (91, 91, null, null, 91, null, 91, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (92, 92, null, null, 92, null, 92, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (93, 93, null, null, 93, null, 93, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (94, 94, null, null, 94, null, 94, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (95, 95, null, null, 95, null, 95, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (96, 96, null, null, 96, 96, 96, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (97, 97, null, null, 97, 97, 97, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (98, 98, null, null, 98, null, 98, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (99, 99, null, null, 99, 99, 99, null);
insert into PROPUESTAS (ID, ID_PERSONA, ID_HOTEL, ID_HOSTEL, ID_VIVIENDA_EXPRESS, ID_APARTAMENTO, ID_VIVIENDA_UNIVERSITARIA, ID_HABITACION) values (100, 100, null, null, 100, 100, 100, null);











-- RESERVAS
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (1, 1, 1, '2016-02-06 21:48:01', null, '2018-01-19 08:49:01', 64, 40003.88);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (2, 2, 2, '2016-03-18 19:29:05', null, '2018-02-18 13:47:09', 79, 60485.04);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (3, 3, 3, '2016-04-28 12:46:12', null, '2018-01-13 09:32:53', 86, 327293.96);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (4, 4, 4, '2016-04-04 20:39:22', null, '2018-02-10 06:11:46', 16, 66594.46);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (5, 5, 5, '2016-07-06 18:10:45', null, '2018-02-15 17:35:22', 71, 285002.89);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (6, 6, 6, '2016-05-29 15:42:20', null, '2018-02-12 09:30:48', 71, 296864.53);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (7, 7, 7, '2016-05-23 23:47:13', null, '2018-01-23 18:41:02', 75, 307627.55);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (8, 8, 8, '2016-04-20 02:45:00', null, '2018-02-14 16:42:59', 12, 119951.29);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (9, 9, 9, '2016-05-09 18:27:47', '2017-07-04 10:06:20', '2018-02-02 21:14:07', 40, 221244.39);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (10, 10, 10, '2016-05-19 20:03:06', null, '2018-02-14 11:52:26', 29, 187501.78);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (11, 11, 11, '2016-06-19 21:27:58', null, '2018-02-14 22:21:26', 4, 346899.61);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (12, 12, 12, '2016-03-13 06:48:16', null, '2018-01-02 22:43:43', 79, 234276.36);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (13, 13, 13, '2016-06-19 09:30:10', null, '2018-01-14 19:58:07', 55, 301451.95);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (14, 14, 14, '2016-05-13 12:40:09', '2017-06-02 13:28:15', '2018-01-31 06:39:59', 10, 226843.11);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (15, 15, 15, '2016-01-18 05:02:20', '2017-08-14 01:24:05', '2018-02-17 18:46:59', 97, 283616.38);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (16, 16, 16, '2016-03-22 18:01:31', null, '2018-02-27 08:37:51', 3, 136446.35);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (17, 17, 17, '2016-01-31 14:04:31', null, '2018-01-22 09:32:54', 19, 141923.35);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (18, 18, 18, '2016-06-24 00:21:46', null, '2018-01-12 07:53:16', 100, 346030.71);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (19, 19, 19, '2016-06-28 02:46:28', null, '2018-03-13 18:47:23', 3, 301808.91);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (20, 20, 20, '2016-04-13 19:11:01', null, '2018-03-14 05:08:42', 34, 60059.37);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (21, 21, 21, '2016-06-18 20:54:17', null, '2018-02-02 15:10:24', 51, 226841.74);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (22, 22, 22, '2016-04-05 06:07:24', null, '2018-02-01 01:57:11', 75, 367735.21);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (23, 23, 23, '2016-02-15 16:16:40', null, '2018-01-06 01:24:07', 52, 94052.68);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (24, 24, 24, '2016-03-26 21:55:52', null, '2018-01-06 21:00:18', 7, 63629.36);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (25, 25, 25, '2016-03-22 20:01:34', null, '2018-02-12 06:50:47', 2, 164071.74);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (26, 26, 26, '2016-06-30 02:05:42', null, '2018-01-05 18:56:38', 21, 218786.45);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (27, 27, 27, '2016-04-15 15:42:20', null, '2018-02-16 06:11:35', 65, 227857.54);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (28, 28, 28, '2016-04-07 22:52:20', null, '2018-01-09 02:10:19', 65, 234416.69);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (29, 29, 29, '2016-03-27 09:08:27', '2017-08-20 14:20:43', '2018-02-24 23:43:31', 69, 281287.21);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (30, 30, 30, '2016-01-30 12:34:43', null, '2018-03-10 06:43:35', 88, 398365.8);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (31, 31, 31, '2016-04-04 02:25:46', null, '2018-02-01 08:59:06', 70, 215771.88);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (32, 32, 32, '2016-04-08 07:16:11', null, '2018-01-11 04:37:07', 34, 250026.67);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (33, 33, 33, '2016-04-08 20:43:13', null, '2018-03-01 07:18:57', 14, 201865.52);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (34, 34, 34, '2016-05-21 05:12:00', null, '2018-01-21 20:11:31', 76, 287593.15);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (35, 35, 35, '2016-03-20 16:26:47', null, '2018-03-03 20:51:12', 28, 290755.53);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (36, 36, 36, '2016-04-01 08:35:05', null, '2018-03-02 23:48:21', 19, 222142.46);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (37, 37, 37, '2016-05-17 14:25:34', null, '2018-03-12 01:32:33', 24, 104163.22);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (38, 38, 38, '2016-06-26 09:06:07', null, '2018-01-14 14:17:11', 91, 204332.01);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (39, 39, 39, '2016-07-08 02:17:41', null, '2018-02-26 08:14:37', 70, 35309.65);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (40, 40, 40, '2016-04-30 07:18:46', null, '2018-02-11 11:25:39', 71, 372012.57);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (41, 41, 41, '2016-04-25 14:44:59', null, '2018-01-16 04:52:14', 81, 115577.8);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (42, 42, 42, '2016-05-31 19:07:08', null, '2018-01-01 14:34:02', 74, 325415.06);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (43, 43, 43, '2016-02-02 19:44:26', null, '2018-02-11 01:35:58', 49, 211849.05);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (44, 44, 44, '2016-02-16 17:48:40', null, '2018-02-18 15:10:44', 81, 138471.67);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (45, 45, 45, '2016-02-28 12:50:38', null, '2018-02-14 03:50:01', 17, 212519.57);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (46, 46, 46, '2016-04-21 04:49:34', null, '2018-02-17 21:10:13', 18, 381546.0);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (47, 47, 47, '2016-03-27 03:23:45', null, '2018-01-25 04:04:33', 88, 99363.79);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (48, 48, 48, '2016-01-17 15:21:33', null, '2018-01-01 03:55:01', 44, 278827.0);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (49, 49, 49, '2016-06-21 15:33:41', null, '2018-01-07 14:10:57', 10, 228782.78);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (50, 50, 50, '2016-02-24 21:40:57', null, '2018-01-17 12:52:07', 52, 393416.48);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (51, 51, 51, '2016-05-28 11:59:59', null, '2018-02-06 16:49:20', 37, 240258.95);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (52, 52, 52, '2016-05-14 13:41:09', null, '2018-01-02 09:29:50', 74, 246620.73);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (53, 53, 53, '2016-06-25 06:41:34', null, '2018-01-17 15:42:03', 6, 204657.3);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (54, 54, 54, '2016-04-05 07:44:33', null, '2018-02-04 18:09:03', 72, 334392.77);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (55, 55, 55, '2016-06-11 02:45:33', null, '2018-01-30 07:50:58', 2, 135069.54);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (56, 56, 56, '2016-04-14 21:09:25', null, '2018-01-25 06:13:29', 78, 392276.77);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (57, 57, 57, '2016-01-19 23:21:38', null, '2018-02-19 12:16:32', 26, 330973.01);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (58, 58, 58, '2016-05-01 18:47:01', null, '2018-01-16 15:33:53', 36, 270389.89);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (59, 59, 59, '2016-06-24 10:32:00', null, '2018-02-10 09:22:09', 78, 381282.64);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (60, 60, 60, '2016-02-01 22:02:19', null, '2018-01-31 08:39:35', 58, 203559.66);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (61, 61, 61, '2016-02-16 15:07:22', null, '2018-03-07 20:09:47', 71, 308162.19);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (62, 62, 62, '2016-06-14 06:10:58', null, '2018-01-23 14:53:05', 27, 196469.02);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (63, 63, 63, '2016-04-16 12:49:56', null, '2018-01-31 09:05:59', 26, 326957.58);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (64, 64, 64, '2016-06-10 08:44:48', null, '2018-01-27 09:52:29', 63, 358565.62);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (65, 65, 65, '2016-03-13 04:17:31', null, '2018-01-24 09:11:40', 27, 262097.87);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (66, 66, 66, '2016-05-16 06:08:21', '2017-07-30 18:32:09', '2018-02-14 06:35:53', 21, 222747.35);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (67, 67, 67, '2016-01-24 03:49:27', null, '2018-02-03 10:28:11', 29, 358799.25);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (68, 68, 68, '2016-05-07 10:55:49', null, '2018-02-27 23:45:15', 14, 30957.35);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (69, 69, 69, '2016-04-18 19:08:48', null, '2018-02-15 19:52:19', 75, 362170.19);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (70, 70, 70, '2016-03-06 23:45:33', null, '2018-01-19 03:51:52', 3, 82505.74);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (71, 71, 71, '2016-02-03 12:54:34', null, '2018-01-05 09:50:57', 44, 254754.65);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (72, 72, 72, '2016-01-25 07:33:02', null, '2018-02-05 13:48:38', 72, 273681.61);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (73, 73, 73, '2016-04-11 20:40:16', null, '2018-02-18 12:39:18', 22, 97054.05);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (74, 74, 74, '2016-02-04 00:17:36', null, '2018-01-16 16:47:50', 93, 358486.18);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (75, 75, 75, '2016-02-26 02:40:15', null, '2018-01-13 06:50:00', 67, 212919.0);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (76, 76, 76, '2016-02-10 11:20:52', null, '2018-01-10 14:44:00', 92, 172720.4);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (77, 77, 77, '2016-04-23 08:33:00', null, '2018-03-14 13:55:16', 89, 205916.1);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (78, 78, 78, '2016-03-29 19:51:36', null, '2018-02-26 12:59:38', 54, 335548.18);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (79, 79, 79, '2016-04-06 23:43:16', null, '2018-01-28 10:54:12', 71, 352294.74);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (80, 80, 80, '2016-06-07 02:25:58', null, '2018-02-19 08:30:41', 77, 316967.5);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (81, 81, 81, '2016-02-26 05:32:58', null, '2018-02-21 00:44:36', 63, 185038.45);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (82, 82, 82, '2016-05-30 13:53:34', null, '2018-02-02 09:25:10', 21, 351432.0);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (83, 83, 83, '2016-04-06 11:09:59', null, '2018-02-13 06:04:18', 81, 65201.26);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (84, 84, 84, '2016-04-21 08:42:15', null, '2018-02-09 21:48:59', 26, 378328.16);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (85, 85, 85, '2016-06-02 07:58:07', null, '2018-03-11 19:53:30', 12, 329135.82);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (86, 86, 86, '2016-03-07 06:55:04', null, '2018-03-14 14:02:53', 57, 358499.59);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (87, 87, 87, '2016-07-06 18:56:34', null, '2018-02-17 12:57:51', 79, 139652.8);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (88, 88, 88, '2016-03-06 11:23:41', null, '2018-01-03 14:21:58', 24, 46839.76);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (89, 89, 89, '2016-01-24 02:25:59', null, '2018-02-10 10:49:21', 81, 343343.14);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (90, 90, 90, '2016-02-01 19:09:56', '2017-03-18 03:55:02', '2018-02-11 01:45:21', 28, 168629.4);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (91, 91, 91, '2016-01-28 09:29:33', null, '2018-02-09 17:26:48', 19, 396020.87);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (92, 92, 92, '2016-05-17 06:43:15', null, '2018-02-02 04:46:18', 34, 132882.12);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (93, 93, 93, '2016-03-23 00:24:28', null, '2018-02-14 04:05:55', 74, 186178.67);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (94, 94, 94, '2016-06-03 15:49:56', null, '2018-03-08 00:14:50', 87, 331087.24);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (95, 95, 95, '2016-06-18 06:11:18', null, '2018-03-09 04:31:12', 7, 200232.47);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (96, 96, 96, '2016-01-14 04:17:23', null, '2018-02-13 13:44:04', 51, 169240.9);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (97, 97, 97, '2016-04-06 16:12:39', null, '2018-01-29 00:05:34', 75, 196843.21);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (98, 98, 98, '2016-02-06 02:24:08', null, '2018-02-11 10:04:44', 29, 54868.32);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (99, 99, 99, '2016-03-16 14:27:33', null, '2018-02-10 10:39:13', 6, 393920.67);
insert into RESERVAS (ID, ID_PERSONA, ID_PROPUESTA, FECHA_REGISTRO, FECHA_CANCELACION, FECHA_INICIO_ESTADIA, DURACION_CONTRATO, COSTO_TOTAL) values (100, 100, 100, '2016-01-29 14:47:28', null, '2018-02-19 11:33:19', 38, 285860.83);













