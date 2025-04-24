DO $$
BEGIN
INSERT INTO personaje (id, nombre, vida, peso_maximo)
VALUES (
        5000,
           'PJ_Pesado',
           100,
           25000
       );
FOR i IN 1..20000 LOOP
        INSERT INTO item (nombre, peso, owner_id) VALUES(
            'Item_' || i,
            1,
            5000
        );
END LOOP;
END
$$;
