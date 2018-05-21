



CREATE INDEX TIPO_INMUEBLE_INDEX ON PROPUESTAS(ID, TIPO_INMUEBLE);

CREATE INDEX P_FIE_INDEX ON RESERVAS(ID_PROPUESTA, FECHA_INICIO_ESTADIA);


-- Posible indexes secundarios = 2^n
-- Posible index primario = 1


-- RFC 10 Consultar Consumo en Alohandes
-- Como parte del requerimiento, el ususario ingresa el identificador de la oferta, el rango de fechas 
-- y el orden en el que quiere recibir la información

-- para el administrador
SELECT PER.*, PP.TIPO 
FROM PERSONAS PER 
INNER JOIN (

    SELECT R.ID_PERSONA AS PERSO, P.TIPO_INMUEBLE AS TIPO 
    FROM RESERVAS R  
    INNER JOIN PROPUESTAS P
    ON P.ID = R.ID_PROPUESTA
    WHERE P.ID = 12 
    AND R.FECHA_INICIO_ESTADIA BETWEEN '2018-01-18' AND '2018-01-28'

) 
PP ON PP.PERSO = PER.ID

ORDER BY PP.TIPO
;

-- para el cliente
SELECT PER.*, PP.TIPO 
FROM PERSONAS PER 
INNER JOIN (

    SELECT R.ID_PERSONA AS PERSO, P.TIPO_INMUEBLE AS TIPO 
    FROM RESERVAS R  
    INNER JOIN PROPUESTAS P
    ON P.ID = R.ID_PROPUESTA

    WHERE P.ID = 1 
    AND R.FECHA_INICIO_ESTADIA BETWEEN '2018-01-18' AND '2018-01-28'

) 
PP ON PP.PERSO = PER.ID

where per.id = 47

ORDER BY PP.TIPO
;










-- RFC 11 Version contratia al anterior


-- para el administrador
SELECT PER.*, PP.TIPO 
FROM PERSONAS PER 
INNER JOIN (

    SELECT R.ID_PERSONA AS PERSO, P.TIPO_INMUEBLE AS TIPO 
    FROM RESERVAS R  
    INNER JOIN PROPUESTAS P
    ON P.ID = R.ID_PROPUESTA

    WHERE P.ID = 1 
    AND R.FECHA_INICIO_ESTADIA NOT BETWEEN '2018-01-18' AND '2018-01-28'

) 
PP ON PP.PERSO = PER.ID

ORDER BY PP.TIPO
;


-- para el cliente
SELECT PER.*, PP.TIPO 
FROM PERSONAS PER 
INNER JOIN (

    SELECT R.ID_PERSONA AS PERSO, P.TIPO_INMUEBLE AS TIPO 
    FROM RESERVAS R  
    INNER JOIN PROPUESTAS P
    ON P.ID = R.ID_PROPUESTA

    WHERE P.ID = 1 
    AND R.FECHA_INICIO_ESTADIA NOT BETWEEN '2018-01-18' AND '2018-01-28'

) 
PP ON PP.PERSO = PER.ID

where per.id = 47

ORDER BY PP.TIPO
;









-- RFC 12

-- POR SEMANA MAYOR DEMANDA OCUPACIONAL
SELECT to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW')) AS "SEMANA",  COUNT (to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW'))) AS "CANTIDAD RESERVAS"
FROM RESERVAS R
INNER JOIN PROPUESTAS P ON
R.ID_PROPUESTA = P.ID
WHERE P.TIPO_INMUEBLE = 'Apartamento'
AND ( R.HAY_MULTA IS NULL 
OR R.HAY_MULTA = 0 )
GROUP BY to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW'))
ORDER BY "CANTIDAD RESERVAS" DESC;


-- POR SEMANA menor DEMANDA OCUPACIONAL
SELECT to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW')) AS "SEMANA",  COUNT (to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW'))) AS "CANTIDAD RESERVAS"
FROM RESERVAS R
INNER JOIN PROPUESTAS P ON
R.ID_PROPUESTA = P.ID
WHERE P.TIPO_INMUEBLE = 'Apartamento'
AND ( R.HAY_MULTA IS NULL 
OR R.HAY_MULTA = 0 )
GROUP BY to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW'))
ORDER BY "CANTIDAD RESERVAS";


-- operadores y demanda
SELECT  W."SEMANA", W."CANTIDAD RESERVAS", PRO.* FROM PROPUESTAS PRO

INNER JOIN (

SELECT
to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW')) AS "SEMANA",  
COUNT (to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW'))) AS "CANTIDAD RESERVAS",
r.id_propuesta as "ID Propuesta"

FROM RESERVAS R
INNER JOIN PROPUESTAS P ON
R.ID_PROPUESTA = P.ID

WHERE ( R.HAY_MULTA IS NULL 
OR R.HAY_MULTA = 0 )

GROUP BY to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW')), r.id_propuesta

)
W
ON  W."ID Propuesta" = pro.id
ORDER BY w."SEMANA", w."CANTIDAD RESERVAS" DESC 
;

SELECT  W."SEMANA", W."CANTIDAD RESERVAS", PRO.* FROM PROPUESTAS PRO

INNER JOIN (

SELECT
to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW')) AS "SEMANA",  
COUNT (to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW'))) AS "CANTIDAD RESERVAS",
r.id_propuesta as "ID Propuesta"

FROM RESERVAS R
INNER JOIN PROPUESTAS P ON
R.ID_PROPUESTA = P.ID

WHERE ( R.HAY_MULTA IS NULL 
OR R.HAY_MULTA = 0 )

GROUP BY to_number(to_char(to_date(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'),'WW')), r.id_propuesta

)
W
ON  W."ID Propuesta" = pro.id
ORDER BY w."SEMANA", w."CANTIDAD RESERVAS"  
;
























---RF13---
SELECT
    personas.id AS id1,
    personas.apellido,
    personas.nombre,
    personas.cedula,
    personas.tipo,
    personas.rol,
    personas.nit,
    personas.email,
     COUNT(reservas.id) AS reservasTotales
FROM
    reservas
    INNER JOIN personas ON personas.id = reservas.id_persona
WHERE
    reservas.COSTO_TOTAL >2600000
GROUP BY
    personas.id,
    personas.apellido,
    personas.nombre,
    personas.cedula,
    personas.tipo,
    personas.rol,
    personas.nit,
    personas.email;


SELECT
    personas.id AS id1,
    personas.apellido,
    personas.nombre,
    personas.cedula,
    personas.tipo,
    personas.rol,
    personas.nit,
    personas.email,
     COUNT(reservas.id) AS reservasTotales
FROM
    reservas
    INNER JOIN personas ON personas.id = reservas.id_persona
WHERE
    reservas.fecha_registro < '2016-12-31'
GROUP BY
    personas.id,
    personas.apellido,
    personas.nombre,
    personas.cedula,
    personas.tipo,
    personas.rol,
    personas.nit,
    personas.email;
SELECT
     COUNT(reservas.id) AS reservastotales,
    propuestas.tipo_inmueble,
    hoteles.tipo_habitacion,
    personas.id AS id1,
   
    personas.nombre,
    personas.apellido,
    personas.cedula,
    personas.tipo,
    personas.rol,
    personas.nit,
    personas.email
FROM
    reservas
    INNER JOIN propuestas ON propuestas.id = reservas.id_propuesta
    INNER JOIN hoteles ON hoteles.id = propuestas.id_hotel
    INNER JOIN personas ON personas.id = reservas.id_persona
WHERE TIPO_HABITACION='suite'
GROUP BY
    propuestas.tipo_inmueble,
    hoteles.tipo_habitacion,
    personas.id,
    personas.nombre,
    personas.apellido,
    personas.cedula,
    personas.tipo,
    personas.rol,
    personas.nit,
    personas.email;





