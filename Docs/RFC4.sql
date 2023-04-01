-- RFC4: MOSTRAR LOS ALOJAMIENTOS DISPONIBLES EN UN RANGO DE FECHAS, QUE CUMPLEN CON UN CONJUNTO DE SERVICIOS

SELECT A_Alojamiento.*
FROM A_Alojamiento
LEFT OUTER JOIN A_Dispone ON A_Alojamiento.idAlojamiento = A_Dispone.idAlojamiento
LEFT OUTER JOIN A_Servicio ON A_Servicio.idServicio = A_Dispone.idServicio
LEFT OUTER JOIN A_Reserva ON A_Reserva.idAlojamiento = A_Alojamiento.idAlojamiento
    WHERE (((A_Reserva.fechaInicio NOT BETWEEN '23/06/2023' AND '27/06/2023') AND  (A_Reserva.fechaFinal NOT BETWEEN '23/06/2023' AND '27/06/2023')) OR
           ((A_Reserva.fechaInicio BETWEEN '23/06/2023' AND '27/06/2023') AND  (A_Reserva.fechaFinal BETWEEN '23/06/2023' AND '27/06/2023') AND 
           A_Reserva.estado = 'Cancelada')) AND A_Servicio.nombre = 'Lavanderia';