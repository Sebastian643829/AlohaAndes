DELETE FROM A_Alojamiento
WHERE A_Alojamiento.idAlojamiento = ? AND ? < NOT EXISTS(SELECT A_Reserva.fechaFinal
                                                         FROM A_alojamiento
                                                         LEFT OUTER JOIN A_Reserva ON A_Reserva.idAlojamiento = A_Alojamiento.idAlojamiento
                                                         WHERE A_alojamiento.idAlojamiento = ? AND A_Reserva.estado = 'Activa');

