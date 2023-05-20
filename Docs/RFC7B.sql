WITH ReservasPorSemana AS (
  SELECT
    TRUNC(FECHAINICIO, 'IW') AS Semana,
    SUM(COSTOTOTAL) AS Ingresos
  FROM A_RESERVA R
  JOIN A_ALOJAMIENTO A ON R.IDALOJAMIENTO = A.IDALOJAMIENTO
  WHERE R.ESTADO = 'Finalizada' AND TIPO = 'ViviendaUniversitaria'
  GROUP BY TRUNC(FECHAINICIO, 'IW')
)
SELECT
  Semana,
  Ingresos
FROM ReservasPorSemana
WHERE Ingresos = (SELECT MAX(Ingresos) FROM ReservasPorSemana);