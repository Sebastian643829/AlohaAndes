SELECT fechaInicial,
    MAX(OCCUPATION) AS MAX_OCUPACION,
    MIN(OCCUPATION) AS MIN_OCUPACION,
    MAX(SOLICITADOS) AS MAX_SOLICITADOS,
    MIN(SOLICITADOS) AS MIN_SOLICITADOS
FROM(
    SELECT TRUNC(a_reserva.fechaInicio, 'IW') fechaInicial,
           COUNT(a_reserva.idAlojamiento) AS OCCUPATION,
           COUNT(a_alojamiento.idOperador) AS SOLICITADOS
    FROM a_reserva
    INNER JOIN a_alojamiento ON a_alojamiento.idalojamiento = a_reserva.idalojamiento
    GROUP BY TRUNC(a_reserva.fechaInicio, 'IW')
    ) subquery
GROUP BY fechaInicial;