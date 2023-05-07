SELECT A_Cliente.* 
FROM A_Cliente
LEFT OUTER JOIN A_Reserva ON A_Cliente.idCliente = A_reserva.idCliente
WHERE A_Reserva.idAlojamiento = 37
GROUP BY A_Cliente.idCliente, A_Cliente.tipoIdentificacion, A_Cliente.nombreCliente, A_Cliente.fechaNacimiento
HAVING COUNT(A_Reserva.idReserva) >= 3 OR SUM(A_Reserva.duracion) >= 15; 

