




SELECT fechaInicial,
    MAX(CASE WHEN OCCUPATION = MAX_OCUPACION THEN idAlojamiento END) AS ID_MAX_OCUPACION,
    MIN(CASE WHEN OCCUPATION = MIN_OCUPACION THEN idAlojamiento END) AS ID_MIN_OCUPACION
FROM(
    SELECT a_reserva.idAlojamiento, 
           TRUNC(a_reserva.fechaInicio, 'IW') fechaInicial,
           SUM(a_reserva.numPersonas) AS OCCUPATION,
           MAX(SUM(a_reserva.numPersonas)) OVER (PARTITION BY TRUNC(a_reserva.fechaInicio, 'IW')) AS MAX_OCUPACION,
           MIN(SUM(a_reserva.numPersonas)) OVER (PARTITION BY TRUNC(a_reserva.fechaInicio, 'IW')) AS MIN_OCUPACION
    FROM a_reserva
    GROUP BY a_reserva.idAlojamiento, TRUNC(a_reserva.fechaInicio, 'IW')
) subquery
GROUP BY fechaInicial
ORDER BY fechaInicial;


SELECT fechaInicial,
    MAX(CASE WHEN SOLICITADOS = MAX_SOLICITADOS THEN idOperador END) AS ID_MAX_SOLICITADOS,
    MIN(CASE WHEN SOLICITADOS = MIN_SOLICITADOS THEN idOperador END) AS ID_MIN_SOLICITADOS
FROM(
    SELECT a_alojamiento.idOperador, TRUNC(a_reserva.fechaInicio, 'IW') fechaInicial,
           COUNT(a_alojamiento.idOperador) AS SOLICITADOS,
           MAX(COUNT(a_alojamiento.idOperador)) OVER (PARTITION BY TRUNC(a_reserva.fechaInicio, 'IW')) AS MAX_SOLICITADOS,
           MIN(COUNT(a_alojamiento.idOperador)) OVER (PARTITION BY TRUNC(a_reserva.fechaInicio, 'IW')) AS MIN_SOLICITADOS
    FROM a_reserva
    INNER JOIN a_alojamiento ON a_alojamiento.idalojamiento = a_reserva.idalojamiento
    GROUP BY a_alojamiento.idOperador, TRUNC(a_reserva.fechaInicio, 'IW')
) subquery
GROUP BY fechaInicial
ORDER BY fechaInicial;

