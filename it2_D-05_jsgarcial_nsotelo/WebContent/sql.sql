




-- RF 7 RESERVAS COLECTIVAS
select * from RESERVAS ORDER BY ID desc ;

UPDATE RESERVAS R SET R.ID_COLECTIVO = null ;

delete from reservas r where r.id > 799;

COMMIT;



-- RC 7 CONSULTA DEMANDAS


-- por inicio de estadia, mayor demanda ocupacion por mes
SELECT EXTRACT ( MONTH FROM  TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS')) AS "MES", COUNT (EXTRACT ( MONTH FROM  TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'))) AS "CANTIDAD"
FROM RESERVAS R
GROUP BY EXTRACT ( MONTH FROM  TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'));


SELECT * FROM RESERVAS;

-- ahora por suma del total de ingresos por mes
SELECT EXTRACT ( MONTH FROM  TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS')) AS "MES", COUNT (EXTRACT ( MONTH FROM  TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'))) AS "CANTIDAD", SUM(R.COSTO_TOTAL)
FROM RESERVAS R
GROUP BY EXTRACT ( MONTH FROM  TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'));


-- menor ocupacion
SELECT EXTRACT ( MONTH FROM  TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS')) AS "MES", COUNT (EXTRACT ( MONTH FROM  TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'))) AS "CANTIDAD"
FROM RESERVAS R
GROUP BY EXTRACT ( MONTH FROM  TO_DATE(R.FECHA_INICIO_ESTADIA,'YYYY-MM-DD HH24:MI:SS'))
order by "CANTIDAD";




-- RF 8 CLIENTES FRECUENTES POR ALOJAMIETO DADO ::  ESTE PARA APARTAMENTO
SELECT ID_PERSONA, COUNT(ID_PERSONA) AS "CANTIDAD"
FROM RESERVAS  WHERE ID_PROPUESTA IN (
    SELECT P.ID FROM PROPUESTAS P WHERE P.ID_APARTAMENTO IS NOT NULL
)
GROUP BY ID_PERSONA ORDER BY "CANTIDAD" DESC;


select * from propuestas;


SELECT * FROM PROPUESTAS WHERE ID_APARTAMENTO IS NOT NULL;



COMMIT;