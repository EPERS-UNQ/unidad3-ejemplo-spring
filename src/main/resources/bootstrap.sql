DO $$
BEGIN
FOR i IN 1..200000 LOOP
        INSERT INTO personaje (nombre, vida, peso_maximo)
        VALUES (
            'PJ_' || i,
            (i % 100) + 1,
            (i % 50) + 10
        );
END LOOP;
END
$$;
