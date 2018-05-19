
select COUNT(*) from PERSONAS;
DELETE FROM HOTEL_TIENE_SERVICIOS;
COMMIT;

DECLARE
   ID NUMBER(20) :=1;
   MAXIMO NUMBER(20) := 250000;
   
   ID_serv NUMBER(20) := 1;
BEGIN
   WHILE ID <= MAXIMO 
   LOOP
   
   INSERT INTO 
   HOTEL_TIENE_SERVICIOS( ID_servicio_basico, id_hotel, id_hostel  ) 
   VALUES ( ID_serv, ID, ID );
   
   ID := ID + 1;
   
   IF ( ID_serv < 6 ) THEN
   ID_serv := ID_serv + 1;
   ELSE ID_serv := 1;
   END IF;
    
   END LOOP;
END;
