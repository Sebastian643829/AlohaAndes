SELECT A_Alojamiento.*
FROM A_Alojamiento
LEFT OUTER JOIN A_Reserva ON A_Reserva.idAlojamiento = A_Alojamiento.idAlojamiento
WHERE ((A_Reserva.fechaInicio IS NULL) OR ((CURRENT_DATE - 30) > A_Reserva.fechaInicio  AND  (CURRENT_DATE - 30) > A_Reserva.fechaFinal) OR (CURRENT_DATE < A_Reserva.fechaInicio  AND  CURRENT_DATE < A_Reserva.fechaFinal)) AND (A_Reserva.estado != 'Cancelada')
GROUP BY A_Alojamiento.idalojamiento, A_Alojamiento.nombre, A_Alojamiento.capacidad , A_Alojamiento.ubicacion, A_Alojamiento.tamano, A_Alojamiento.precionoche, A_Alojamiento.ocupacionactual, A_Alojamiento.numreservas, A_Alojamiento.idoperador, A_Alojamiento.estado, A_Alojamiento.tipo
ORDER BY A_Alojamiento.idalojamiento;