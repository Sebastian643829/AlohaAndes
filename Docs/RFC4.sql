-- RFC4: MOSTRAR LOS ALOJAMIENTOS DISPONIBLES EN UN RANGO DE FECHAS, QUE CUMPLEN CON UN CONJUNTO DE SERVICIOS

SELECT A_Alojamiento.*
FROM A_Alojamiento
LEFT OUTER JOIN A_Dispone ON A_Alojamiento.idAlojamiento = A_Dispone.idAlojamiento
LEFT OUTER JOIN A_Servicio ON A_Servicio.idServicio = A_Dispone.idServicio
LEFT OUTER JOIN A_Reserva ON A_Reserva.idAlojamiento = A_Alojamiento.idAlojamiento
    WHERE (((A_Reserva.fechaInicio NOT BETWEEN '2023-11-02' AND '2023-12-02') AND (A_Reserva.fechaFinal NOT BETWEEN ? AND ?)) OR
           ((A_Reserva.fechaInicio BETWEEN ? AND ?) OR (A_Reserva.fechaFinal BETWEEN ? AND ?) AND 
           A_Reserva.estado = 'Cancelada')) AND A_Servicio.nombre = 'Lavanderia'
GROUP BY A_Alojamiento.idAlojamiento;