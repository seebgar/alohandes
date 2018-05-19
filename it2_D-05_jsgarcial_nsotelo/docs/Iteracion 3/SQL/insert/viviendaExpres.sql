DECLARE
   ID NUMBER(38) :=1;
 MAXIMO NUMBER(20) := 100000;
   numeroH number(1) :=1;

   cap NUMBER(1):= 1;

BEGIN
   WHILE ID <= MAXIMO 
   LOOP
   DBMS_OUTPUT.PUT_LINE('This loop has executed'||TO_CHAR(MAXIMO)||' time(s)');
   INSERT INTO 
   VIVIENDAS_EXPRESS
   (ID,NUMERO_HABITACIONES,MENAJE,UBICACION,CAPACIDAD_MAXIMA)
   VALUES
   (ID,
   numeroH,
   'lorem',
   'Carrera7-53',
   cap);

   
   ID := ID+ 1;
   

  
   
    IF(numeroH = 5) THEN
       numeroH:=1;
     ELSE
        numeroH:=numeroH+1;
    END IF;
    IF(cap = 1) THEN
        cap:=5;
     ELSE
        cap:=cap-1;
    END IF;
    
   END LOOP;
END;