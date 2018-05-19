DELETE FROM APARTAMENTOS;

DECLARE
   ID NUMBER(20) :=1;
   MAXIMO NUMBER(20) := 250000;
   MUEBLE NUMBER(1) := 0;
   COSTO NUMBER(20) := 120;
   CAP NUMBER(20) := 2;
BEGIN
   WHILE ID <= MAXIMO 
   LOOP
   INSERT INTO 
   APARTAMENTOS( ID, AMOBLADO, COSTO_ADMIN, CAPACIDAD_MAXIMA ) 
   VALUES ( ID, mueble, COSTO, cap );
   
   ID := ID + 1;
   
    IF(MUEBLE = 0 ) THEN
        MUEBLE := 1;
    ELSE
        MUEBLE := 0;
    END IF;
    
    IF ( COSTO >= 340 ) THEN
        COSTO := COSTO - 97;
    ELSE
        COSTO := COSTO + 26;
    END IF;
    
    IF ( CAP >= 20 ) THEN
        CAP := CAP - 3;
    ELSE
        CAP := CAP + 1;
    END IF;
    
   END LOOP;
END;