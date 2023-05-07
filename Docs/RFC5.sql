-- parte 1
SELECT A_MiembroActivo.tipo, COUNT(A_Reserva.idCliente) AS Numero_reservas, SUM(A_Reserva.duracion) AS Num_nochesReservadas, SUM(A_Reserva.costoTotal) AS Dinero_pagado
FROM A_cliente
INNER JOIN A_MiembroActivo ON A_MiembroActivo.idMiembroActivo = A_cliente.idCliente
LEFT OUTER JOIN A_reserva ON A_Reserva.idCliente = A_cliente.idCliente
GROUP BY A_MiembroActivo.tipo;

-- parte 2
SELECT A_MiembroSecundario.tipo, COUNT(A_Reserva.idCliente) AS Numero_reservas, SUM(A_Reserva.duracion) AS Num_nochesReservadas, SUM(A_Reserva.costoTotal) AS Dinero_pagado
FROM A_cliente
INNER JOIN A_MiembroSecundario ON A_MiembroSecundario.idMiembroSecundario = A_cliente.idCliente
LEFT OUTER JOIN A_reserva ON A_Reserva.idCliente = A_cliente.idCliente
GROUP BY A_MiembroSecundario.tipo;