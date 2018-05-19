DELETE FROM habitaciones;

DECLARE
   ID NUMBER(20) :=1;
   MAXIMO NUMBER(20) := 250000;
   
   precio NUMBER(1) := 0;
   CAP NUMBER(20) := 1;
   TIPO VARCHAR(25) := 'sencilla';
BEGIN
   WHILE ID <= MAXIMO 
   LOOP
   
   IF ( CAP >= 5 ) THEN
        CAP := 1;
    ELSE
        CAP := CAP + 1;
    END IF;
    
    IF ( CAP = 2 ) THEN
        TIPO := 'doble';
    ELSE
        IF ( CAP = 1 ) THEN 
            TIPO := 'sencilla';  
        ELSE
            TIPO := 'compartida';
        END IF;
    END IF;
   
   
   
   INSERT INTO 
   habitaciones( ID, PRECIO_ESPECIAL, TIPO_HABITACION, CAPACIDAD_MAXIMA ) 
   VALUES ( ID, precio, tipo, cap );
   
   ID := ID + 1;
   
    IF( precio = 0 ) THEN
        precio := 1;
    ELSE
        precio := 0;
    END IF;
    
    
   END LOOP;
END;