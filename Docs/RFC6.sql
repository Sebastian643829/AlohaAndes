SELECT A_Reserva.idCliente, COUNT(A_Reserva.idCliente) AS Numero_reservas, SUM(A_Reserva.duracion) AS Num_nochesReservadas, SUM(A_Reserva.costoTotal) AS Dinero_pagado
FROM A_Reserva
WHERE A_Reserva.idCliente = 1111111111
GROUP BY A_Reserva.idCliente;