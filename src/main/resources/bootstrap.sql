DO $$
BEGIN
FOR i IN 1..200000 LOOP
        INSERT INTO personaje (id, nombre, vida, peso_maximo)
        VALUES (
            i,
            'PJ_' || i,
            (i % 100) + 1,
            (i % 50) + 10
        );
END LOOP;
END
$$;
