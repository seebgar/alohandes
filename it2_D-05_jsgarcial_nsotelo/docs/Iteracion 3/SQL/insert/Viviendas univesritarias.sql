DECLARE
   ID NUMBER(38) :=1;
   MAXIMO NUMBER(20) := 100000;
   tipo VARCHAR2(29) :='sencilla';
   mensual NUMBER(1) := 0;
   cap NUMBER(38):= 1000;

BEGIN
   WHILE ID <= MAXIMO 
   LOOP
   INSERT INTO 
   VIVIENDAS_UNIVERSITARIAS
   (ID, UBICACION, MENAJE,TIPO_HABITACION,MENSUAL,DESCRIPCION,CAPACIDAD_MAXIMA)
   VALUES (ID,'carrera 58','lorem',tipo,mensual,'muy bonita',cap);

   
   ID := ID+ 1;
   

   IF(tipo ='sencilla') THEN 
      tipo := 'compartida';
   ELSE
    tipo:='sencilla';
   END IF;
   
    IF(mensual = 0) THEN
        mensual:=1;
     ELSE
        mensual:=0;
    END IF;
    
    IF(cap = 5000) THEN
        cap:=1000;
     ELSE
        cap:=cap+1;
    END IF;
    
   END LOOP;
END;