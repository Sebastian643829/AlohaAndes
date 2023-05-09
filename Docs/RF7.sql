SELECT a_alojamiento.idalojamiento, a_alojamiento.precionoche
FROM A_alojamiento
LEFT OUTER JOIN A_reserva ON A_alojamiento.idAlojamiento = A_reserva.idAlojamiento
WHERE A_alojamiento.estado = 'Habilitado' AND A_alojamiento.tipo = 'HabitacionVivienda'
AND A_alojamiento.idAlojamiento NOT IN ( SELECT A_reserva.idAlojamiento
                                     FROM A_reserva
                                     WHERE NOT((A_Reserva.fechaInicio IS NULL) OR ((TO_DATE('2023-02-01', 'YYYY-MM-DD') > A_Reserva.fechaInicio  AND  TO_DATE('2023-02-01', 'YYYY-MM-DD') > A_Reserva.fechaFinal OR (A_Reserva.estado = 'Cancelada')) OR (TO_DATE('2023-02-28', 'YYYY-MM-DD') < A_Reserva.fechaInicio  AND  TO_DATE('2023-02-28', 'YYYY-MM-DD') < A_Reserva.fechaFinal OR (A_Reserva.estado = 'Cancelada')))))   
GROUP BY a_alojamiento.idalojamiento, a_alojamiento.precionoche;                            
                                     
                                     