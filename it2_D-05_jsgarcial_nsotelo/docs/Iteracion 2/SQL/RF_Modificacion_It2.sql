-- Iteracion 2
-- Sistemas Transaccionales
-- Grupo D-05

-- Actualizaciones en los valores de Capacidad Maxima en las tablas Propuestas e Inmueble Respectivo para que tuviera sentido la base de datos.

-- Para Apartamentos
UPDATE PROPUESTAS P 
SET P.CAPACIDAD_MAXIMA = 
(   
    SELECT A.CAPACIDAD_MAXIMA 
    FROM APARTAMENTOS A 
    WHERE A.ID = P.ID_APARTAMENTO    
)
WHERE P.ID_APARTAMENTO IS NOT NULL;

-- Para Hoteles
UPDATE PROPUESTAS P 
SET P.CAPACIDAD_MAXIMA = 
(   
    SELECT H.CAPACIDAD_MAXIMA 
    FROM HOTELES H 
    WHERE H.ID = P.ID_HOTEL 
) 
WHERE P.ID_HOTEL IS NOT NULL;

-- Para Hosteles
UPDATE PROPUESTAS P 
SET P.CAPACIDAD_MAXIMA = 
(   
    SELECT H.CAPACIDAD_MAXIMA 
    FROM HOSTELES H 
    WHERE H.ID = P.ID_HOSTEL 
) 
WHERE P.ID_HOSTEL IS NOT NULL;

-- Para Viendas Express
UPDATE PROPUESTAS P 
SET P.CAPACIDAD_MAXIMA = 
(   
    SELECT V.CAPACIDAD_MAXIMA 
    FROM VIVIENDAS_EXPRESS V
    WHERE V.ID = P.ID_VIVIENDA_EXPRESS
) 
WHERE P.ID_VIVIENDA_EXPRESS IS NOT NULL;

-- Para Viviendas Universitarias
UPDATE PROPUESTAS P 
SET P.CAPACIDAD_MAXIMA = 
(   
    SELECT U.CAPACIDAD_MAXIMA 
    FROM VIVIENDAS_UNIVERSITARIAS U
    WHERE U.ID = P.ID_VIVIENDA_UNIVERSITARIA
) 
WHERE P.ID_VIVIENDA_UNIVERSITARIA IS NOT NULL;

-- Para Habitacion
UPDATE PROPUESTAS P 
SET P.CAPACIDAD_MAXIMA = 
(   
    SELECT H.CAPACIDAD_MAXIMA 
    FROM HABITACIONES H
    WHERE H.ID = P.ID_HABITACION
) 
WHERE P.ID_HABITACION IS NOT NULL;


SELECT * FROM PROPUESTAS;








-- REQUERIMIENTOS FUNCIONALES DE MODIFICACION





-- RF 7 REGISTRAR RESERVAS COLECTIVAS
-- Para realizar este metodo se reciben como parametros el tipo de inmueble y una lista de strings que representan losservicios deseados
SELECT P.ID from PROPUESTAS P WHERE UPPER(TIPO_INMUEBLE) = UPPER('" + tipo_inmueble + "') AND P.ID_APARTAMENTO 
IN ( SELECT S.ID_APARTAMENTO FROM SERVICIOS_BASICOS S INNER JOIN TIPOS T ON T.ID = S.ID_TIPO 
    WHERE T.NOMBRE IN ( 'baño', 'tv') );






-- RF8 eliminar una reserva colectiva
Para poder cumplir con este requerimiento funcional se reciben como parámetros el id de la reserva colectiva ademas del uso del RF5 como sub-transaccion.

SELECT * FROM RESERVAS R WHERE R.ID_COLECTIVO = “id_colectivo”;
-- Acá ocurre la sub-transaccion RF5______

UPDATE RESERVAS SET ID_COLECTIVO = NULL WHERE ID = “id_colectivo”;

COMMIT;





-- RF9 deshabitar una oferta de alojamiento 
-- Para poder cumplir con este requerimiento funcional se reciben como parámetros el id del alojamiento ademas del uso del RF5  y RF4 como sub-transacciones.


SELECT * FROM RESERVAS R WHERE R.ID_PROPUESTA = “id_alojamiento”;

SELECT * FROM PROPUESTAS WHERE TIPO_INMUEBLE = 1 AND ID !=“id_alojamiento”;

-- Acá ocurre la sub-transaccion RF5——
-- Acá ocurre la sub-transaccion RF4______

UPDATE PROPUESTAS SET DISPONIBLE = 0 WHERE ID =“id_alojamiento”;

COMMIT;






-- RF10 rehabilitar una oferta de alojamiento Para poder cumplir con este requerimiento funcional se recibe como parámetro el id del alojamiento que se desea rehabilitar

UPDATE PROPUESTAS SET DISPONIBLE = 1 WHERE ID =“id_alojamiento”;

COMMIT;










