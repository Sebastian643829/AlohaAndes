SELECT a_cliente.*
FROM a_cliente
INNER JOIN a_reserva ON a_cliente.idCliente = a_reserva.idCliente
WHERE ((A_Reserva.fechaInicio BETWEEN TO_DATE('2022-12-01', 'YYYY-MM-DD') AND TO_DATE('2023-12-31', 'YYYY-MM-DD') ) AND (A_Reserva.fechaFinal BETWEEN TO_DATE('2022-12-01', 'YYYY-MM-DD') AND TO_DATE('2023-12-31', 'YYYY-MM-DD') ))
GROUP BY a_cliente.idcliente, a_cliente.tipoidentificacion, a_cliente.nombrecliente, a_cliente.fechanacimiento, A_Reserva.fechaInicio, A_Reserva.fechaFinal
ORDER BY a_cliente.idCliente;
