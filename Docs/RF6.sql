DELETE FROM A_Reserva
WHERE A_Reserva.idAlojamiento = ? AND current_date > ALL(SELECT A_Reserva.fechaFinal
                                                         FROM A_Reserva
                                                         LEFT OUTER JOIN A_Alojamiento ON A_Reserva.idAlojamiento = A_Alojamiento.idAlojamiento
                                                         WHERE A_Alojamiento.idAlojamiento = ? AND A_Reserva.estado = 'Activa');

DELETE FROM A_Alojamiento
WHERE A_Alojamiento.idAlojamiento = ?;

