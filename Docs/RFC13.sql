SELECT DISTINCT a_cliente.*
FROM a_cliente
JOIN a_reserva ON a_cliente.idCliente = a_reserva.idCliente
JOIN a_alojamiento ON a_reserva.idAlojamiento = a_alojamiento.idAlojamiento
WHERE
    -- Clientes que hacen reservas al menos una vez al mes
    EXISTS (
        SELECT 1
        FROM a_reserva
        WHERE a_cliente.idCliente = a_reserva.idCliente
        GROUP BY TRUNC(a_reserva.fechaInicio, 'MM')
        HAVING COUNT(*) >= 1
    )
    OR
    -- Clientes que siempre reservan alojamientos costosos
    EXISTS (
        SELECT 1
        FROM a_reserva
        INNER JOIN a_alojamiento ON a_reserva.idAlojamiento = a_alojamiento.idAlojamiento
        WHERE a_cliente.idCliente = a_reserva.idCliente AND a_alojamiento.precioNoche >= 650000
    )
    OR
    -- Clientes que siempre reservan suites
    EXISTS (
        SELECT 1
        FROM a_reserva
        INNER JOIN a_alojamiento ON a_reserva.idAlojamiento = a_alojamiento.idAlojamiento
        INNER JOIN a_habitacionHotel ON a_habitacionHotel.idAlojamiento = a_alojamiento.idAlojamiento
        WHERE a_cliente.idCliente = a_reserva.idCliente AND a_habitacionHotel.tipoHabitacion = 'Suites'
    );