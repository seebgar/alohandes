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

