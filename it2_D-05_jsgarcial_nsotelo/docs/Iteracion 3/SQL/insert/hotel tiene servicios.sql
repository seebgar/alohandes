DECLARE
contador number(38):=1;
MAXIMO number(38):=200000;
   ID_servicio_bh NUMBER(38) :=1;
  id_hotel NUMBER(38) := 10;
id_hostel number(38) :=1;
   

BEGIN
   WHILE contador <= MAXIMO 
   LOOP
   INSERT INTO 
  HOTEL_TIENE_SERVICIOS
   (ID_SERVICIO_BASICO_HOTELERO,ID_HOTEL,ID_HOSTEL)
   VALUES (ID_servicio_bh,id_hotel,id_hostel);

   
   contador := contador+ 1;
   

   IF(ID_servicio_bh=6) THEN 
     ID_servicio_bh:=1;
   ELSE
  ID_servicio_bh:=ID_servicio_bh+1;
   END IF;
   
  
   
    
   END LOOP;
END;