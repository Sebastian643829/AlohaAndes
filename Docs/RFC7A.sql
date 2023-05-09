WITH ReservasPorSemana AS (
  SELECT
    TRUNC(FECHAINICIO, 'IW') AS Semana,
    COUNT(IDRESERVA) AS Ocupacion
  FROM A_RESERVA R
  JOIN A_ALOJAMIENTO A ON R.IDALOJAMIENTO = A.IDALOJAMIENTO
  WHERE TIPO = 'TipoDeAlojamiento' AND (ESTADO = 'Activa' OR ESTADO = 'Finalizada')
  GROUP BY TRUNC(FECHAINICIO, 'IW')
)
SELECT
  Semana,
  Ocupacion
FROM ReservasPorSemana
WHERE Ocupacion = (SELECT MAX(Ocupacion) FROM ReservasPorSemana);
