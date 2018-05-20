
DELETE FROM PERSONAS;

DECLARE
   ID NUMBER(20) :=1;
   MAXIMO NUMBER(20) := 800000;
   
   NOMBRE VARCHAR(50) := 'Nombre';
   APELLIDO VARCHAR(50) := 'Apellido';
   CEDULA NUMBER(20) := 10;
   TIPO VARCHAR(25) := 'empresa';
   ROL VARCHAR(25) := 'operador';
   NIT NUMBER(25) := 10;
   EMAIL VARCHAR(50) := 'email@uniandes.edu.co';
   
   
BEGIN
   WHILE ID <= MAXIMO 
   LOOP
   
   INSERT INTO 
   PERSONAS( ID, NOMBRE, APELLIDO, CEDULA, TIPO, ROL, NIT, EMAIL, COSTO_MULTA ) 
   VALUES ( ID, NOMBRE, APELLIDO, CEDULA, TIPO, ROL, NIT, EMAIL, 0 );
   
   ID := ID + 1;
   
   IF ( TIPO = 'padre' ) THEN
        TIPO := 'estudiante';
    ELSE 
        IF ( TIPO = 'empresa' ) THEN
            TIPO := 'estudiante';
        ELSE 
            TIPO := 'empresa';
            END IF;
    END IF;
    
     IF ( TIPO = 'empresa' ) THEN
        NIT := ID * 100;
        ROL := 'operador';
    ELSE
        TIPO := 'padre';
        NIT := 0;
        ROL := 'cliente';
    END IF;
    
        
   END LOOP;
END;
