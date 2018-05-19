DELETE FROM hoteles;

DECLARE
   ID NUMBER(20) :=1;
   MAXIMO NUMBER(20) := 250000;
   
   precio NUMBER(20) := 0;
   CAP NUMBER(20) := 1;
   TIPO VARCHAR(25) := 'estandar';
   ubi varchar(50) := 'Calle 19 # 13-12';
   TAM VARCHAR(25) := 'mediano';
   h_i number(1) := 0;
   R_S varchar(50) := 'registro ante la superintendencia';
   r_c varchar(50) := 'registro ante la camara de comercio';
BEGIN
   WHILE ID <= MAXIMO 
   LOOP
   
   if ( cap >= 10 ) then
    tam := 'grande';
    else
        if ( cap >= 5 ) then
        tam := 'mediano';
        else
        tam := 'pequeño';
        end if;
    end if;
   
   IF ( CAP >= 25 ) THEN
        CAP := 1;
    ELSE
        CAP := CAP + 1;
    END IF;
    
    IF ( CAP < 4 ) THEN
        TIPO := 'estandar';
    ELSE
        if ( cap >= 4  ) then
            TIPO := 'semisuite';
            else tipo := 'suite';
            end if;
    end if;
    
   
   INSERT INTO 
   hoteles( ID, REGISTRO_CAMARA_COMERCIO, REGISTRO_SUPERINTENDENCIA, TIPO_HABITACION, HORARIO_ADMIN_24H, TAMANO, UBICACION, CANTIDAD_NOCHES, CAPACIDAD_MAXIMA ) 
   VALUES ( ID, r_c, r_s, tipo, h_i, tam, ubi, 8, cap );
   
   ID := ID + 1;
   
    IF(h_i = 0 ) THEN
        h_i := 1;
    ELSE
        h_i := 0;
    END IF;
    
   END LOOP;
END;



