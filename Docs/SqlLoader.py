from operator import index
from random import random
import pandas as pd
from faker import Faker
from collections import defaultdict 
from faker.providers import DynamicProvider


# #utility
# def remove_common(a, b):
#     for i in a[:]:
#         if i in b:
#             a.remove(i)
#             b.remove(i)


fake = Faker()
fake_data = defaultdict(list)
fake_data_operador = defaultdict(list)
fake_data_propietario = defaultdict(list)
fake_data_empresa = defaultdict(list)
fake_data_alojamiento = defaultdict(list)
fake_data_viviendaUniversitaria = defaultdict(list)
fake_data_habitacionVivienda = defaultdict(list)
fake_data_apartamento = defaultdict(list)
fake_data_habitacionHotel = defaultdict(list)
fake_data_hostal = defaultdict(list)
fake_data_viviendaTemporal = defaultdict(list)
fake_data_cliente = defaultdict(list)
fake_data_miembroActivo = defaultdict(list)
fake_data_miembroSecundario = defaultdict(list)
fake_data_servicio = defaultdict(list)
fake_data_dispone = defaultdict(list)
fake_data_reserva = defaultdict(list)

# pendiente
operador_index = []
alojamiento_index = []
cliente_index = []
servicio_index = []
reserva_index = []

clientesHabitacion = {} # pendiente

estado_alojamiento_provider = DynamicProvider(
     provider_name="estado_alojamiento",
     elements=["Habilitado", "Deshabilitado"]
)

tipo_alojamiento_provider = DynamicProvider(
     provider_name="tipo_alojamiento",
     elements=['ViviendaUniversitaria', 'HabitacionVivienda', 'Apartamento', 'HabitacionHotel', 'Hostal', 'ViviendaTemporal']
)

tipo_habitacion_provider = DynamicProvider(
    provider_name="tipo_habitacion",
    elements=['Compartida', 'Individual']
)

tipo_bano_provider = DynamicProvider(
    provider_name = "tipo_bano",
    elements = ['Privado', 'Publico']
)

tipo_identificacion_provider = DynamicProvider(
    provider_name = "tipo_identificacion",
    elements = ["CC", "TI", "PA"]
)

tipo_hotel_provider = DynamicProvider(
    provider_name = "tipo_hotel",
    elements = ['Estandar', 'Semisuites', 'Suites']
)

tipo_miembroActivo_provider = DynamicProvider(
    provider_name = "tipo_miembroActivo",
    elements = ['Estudiante', 'Profesor visitante', 'Empleado', 'Profesor titular']
)

tipo_miembroSecundario_provider = DynamicProvider(
    provider_name = "tipo_miembroSecundario",
    elements = ['Padre', 'Egresado', 'Invitado']
)

estado_reserva_provider = DynamicProvider(
    provider_name = "estado_reserva",
    elements = ['Cancelada', 'Finalizada', 'Activa']
)

tipo_vinculacion_provider = DynamicProvider(
    provider_name = "tipo_vinculacion",
    elements = ['Estudiante', 'Profesor visitante', 'Empleado', 'Profesor titular', 'Padre', 'Egresado', 'Invitado']
)

nombre_servicio_provider = DynamicProvider(
    provider_name = "nombre_servicio",
    elements = ['Piscina', 'Sauna', 'Cocina', 'Turco', 'Spa', 'Gimnasio', 'Comida']
)

ubicacion_provider = DynamicProvider(
    provider_name = "ubicacion",
    elements = ['Norte', 'Sur', 'Este', 'Oeste']
)


fake.add_provider(estado_alojamiento_provider)
fake.add_provider(tipo_alojamiento_provider)
fake.add_provider(tipo_habitacion_provider)
fake.add_provider(tipo_bano_provider)
fake.add_provider(tipo_identificacion_provider)
fake.add_provider(tipo_hotel_provider)
fake.add_provider(tipo_miembroActivo_provider)
fake.add_provider(tipo_miembroSecundario_provider)
fake.add_provider(estado_reserva_provider)
fake.add_provider(tipo_vinculacion_provider)
fake.add_provider(nombre_servicio_provider)
fake.add_provider(ubicacion_provider)

# CSV OPERADOR
for op in range(1,200000):
    fake_data_operador["idOperador"].append( op )
    operador_index.append(op)
    fake_data_operador["telefono"].append( fake.phone_number() )
    fake_data_operador["tipoVinculacion"].append( fake.tipo_vinculacion() )


df_fake_data_operador = pd.DataFrame(fake_data_operador)
df_fake_data_operador.to_csv("csv loader/operadores.csv", index=False)

#Provider para operadores
operador_index_provider = DynamicProvider(
     provider_name="idOperador",
     elements=operador_index
)

# CSV PROPIETARIO
for pro in range(1,100001):
    fake_data_propietario["idOperador"].append( pro )
    operador_index.append(pro)
    fake_data_propietario["identificacion"].append( fake.unique.random_int(1000000000, 1999999999) )
    fake_data_propietario["tipoIdentificacion"].append( fake.tipo_identificacion() )
    fake_data_propietario["nombrePropietario"].append( fake.name() )

df_fake_data_propietario = pd.DataFrame(fake_data_propietario)
df_fake_data_propietario.to_csv("csv loader/propietarios.csv", index=False)

# CSV EMPRESA
for emp in range(100001,200001):
    fake_data_empresa["idOperador"].append( emp )
    operador_index.append(emp)
    fake_data_empresa["nit"].append( fake.unique.random_int(1000000000, 1999999999) )
    fake_data_empresa["nombreEmpresa"].append( fake.company() )

df_fake_data_empresa = pd.DataFrame(fake_data_empresa)
df_fake_data_empresa.to_csv("csv loader/empresas.csv", index=False)

# CSV ALOJAMIENTO
for aloj in range(1,180001):
    fake_data_alojamiento["idAlojamiento"].append( aloj )
    alojamiento_index.append(aloj)
    fake_data_alojamiento["nombre"].append( fake.name() )
    capacidad = fake.random_int(1,100)
    fake_data_alojamiento["capacidad"].append( capacidad )
    fake_data_alojamiento["ubicacion"].append( fake.ubicacion() )
    fake_data_alojamiento["tamano"].append(fake.random_int(1,300))
    fake_data_alojamiento["precioNoche"].append(fake.random_int(20000,2000000) )
    fake_data_alojamiento["ocupacionActual"].append(fake.random_int(1,capacidad))
    fake_data_alojamiento["numReservas"].append( fake.random_int(0, 20) )
    fake_data_alojamiento["idOperador"].append(fake.random_int(1, 200000) )
    fake_data_alojamiento["estado"].append( fake.estado_alojamiento() )
    fake_data_alojamiento["tipo"].append( fake.tipo_alojamiento() )

df_fake_data_alojamiento = pd.DataFrame(fake_data_alojamiento)
df_fake_data_alojamiento.to_csv("csv loader/alojamientos.csv", index=False)

#Provider para alojamientos
alojamiento_index_provider = DynamicProvider(
     provider_name="idAlojamiento",
     elements=alojamiento_index
)

# CSV VIVIENDAUNIVERSITARIA
for vu in range(1, 30001):
    fake_data_viviendaUniversitaria["idAlojamiento"].append( vu )
    alojamiento_index.append(vu)
    fake_data_viviendaUniversitaria["tipoHabitacion"].append( fake.tipo_habitacion())

df_fake_data_viviendaUniversitaria = pd.DataFrame(fake_data_viviendaUniversitaria)
df_fake_data_viviendaUniversitaria.to_csv("csv loader/viviendasUniversitarias.csv", index=False)

# CSV HABITACIONVIVIENDA
for hv in range(30001, 60001):
    fake_data_habitacionVivienda["idAlojamiento"].append( hv )
    alojamiento_index.append(hv)
    fake_data_habitacionVivienda["tipoBano"].append( fake.tipo_bano())
    fake_data_habitacionVivienda["tipoHabitacion"].append( fake.tipo_habitacion())

df_fake_data_habitacionVivienda = pd.DataFrame(fake_data_habitacionVivienda)
df_fake_data_habitacionVivienda.to_csv("csv loader/habitacionesViviendas.csv", index=False)

# CSV APARTAMENTO
for ap in range(60001, 90001):
    fake_data_apartamento["idAlojamiento"].append( ap )
    alojamiento_index.append(ap)

df_fake_data_apartamento = pd.DataFrame(fake_data_apartamento)
df_fake_data_apartamento.to_csv("csv loader/apartamentos.csv", index=False)

# CSV HABITACIONHOTEL
for hh in range(90001, 120001):
    fake_data_habitacionHotel["idAlojamiento"].append( hh )
    alojamiento_index.append(hh)
    fake_data_habitacionHotel["tipoHabitacion"].append( fake.tipo_hotel() )

df_fake_data_habitacionHotel = pd.DataFrame(fake_data_habitacionHotel)
df_fake_data_habitacionHotel.to_csv("csv loader/habitacionesHoteles.csv", index=False)

# CSV HOSTAL
for h in range(120001, 150001):
    fake_data_hostal["idAlojamiento"].append( h )
    alojamiento_index.append(h)
    fake_data_hostal["horarioApertura"].append( fake.time() )
    fake_data_hostal["horarioCierre"].append( fake.time() )

df_fake_data_hostal = pd.DataFrame(fake_data_hostal)
df_fake_data_hostal.to_csv("csv loader/hostales.csv", index=False)

# CSV VIVIENDATEMPORAL
for vt in range(150001, 180001):
    fake_data_viviendaTemporal["idAlojamiento"].append( vt )
    alojamiento_index.append(vt)
    fake_data_viviendaTemporal["numHabitaciones"].append( fake.random_int(1, 10) )

df_fake_data_viviendaTemporal = pd.DataFrame(fake_data_viviendaTemporal)
df_fake_data_viviendaTemporal.to_csv("csv loader/viviendasTemporales.csv", index=False)

# CSV CLIENTE
for cl in range(1, 180001):
    fake_data_cliente["idCliente"].append( cl )
    cliente_index.append(cl)
    fake_data_cliente["tipoIdentificacion"].append( fake.tipo_identificacion() )
    fake_data_cliente["nombreCliente"].append( fake.name() )
    fake_data_cliente["fechaNacimiento"].append( fake.date_of_birth() )

df_fake_data_cliente = pd.DataFrame(fake_data_cliente)
df_fake_data_cliente.to_csv("csv loader/clientes.csv", index=False)

#Provider para alojamientos
cliente_index_provider = DynamicProvider(
     provider_name="idCliente",
     elements=cliente_index
)

# CSV MIEMBROACTIVO
for ma in range(1, 90001):
    fake_data_miembroActivo["idMiembroActivo"].append( ma )
    cliente_index.append(ma)
    fake_data_miembroActivo["carnet"].append( fake.unique.random_int(200000000, 202319999))
    fake_data_miembroActivo["tipo"].append( fake.tipo_miembroActivo() )

df_fake_data_miembroActivo = pd.DataFrame(fake_data_miembroActivo)
df_fake_data_miembroActivo.to_csv("csv loader/miembrosActivos.csv", index=False)


# CSV MIEMBROSECUNDARIO
for ms in range(90001, 180001):
    fake_data_miembroSecundario["idMiembroSecundario"].append( ms )
    cliente_index.append(ms)
    fake_data_miembroSecundario["tipo"].append( fake.tipo_miembroSecundario() )

df_fake_data_miembroSecundario = pd.DataFrame(fake_data_miembroSecundario)
df_fake_data_miembroSecundario.to_csv("csv loader/miembrosSecundarios.csv", index=False)

# CSV SERVICIO
for se in range(1, 180001):
    fake_data_servicio["idServicio"].append( se )
    servicio_index.append(se)
    fake_data_servicio["nombre"].append( fake.nombre_servicio() )
    fake_data_servicio["costo"].append( fake.random_int(0,500000))

df_fake_data_servicio = pd.DataFrame(fake_data_servicio)
df_fake_data_servicio.to_csv("csv loader/servicios.csv", index=False)

#Provider para servicios
servicio_index_provider = DynamicProvider(
     provider_name="idServicio",
     elements=servicio_index
)

# CSV DISPONE
for d in range(1, 180001):
    fake_data_dispone["idServicio"].append( d )
    fake_data_dispone["idAlojammiento"].append( fake.random_int(1,180001) )

df_fake_data_dispone = pd.DataFrame(fake_data_dispone)
df_fake_data_dispone.to_csv("csv loader/disponen.csv", index=False)


# CSV RESERVA
for res in range(1, 200001):
    fake_data_reserva["idReserva"].append( res )
    reserva_index.append(res)
    fake_data_reserva["idAlojammiento"].append( fake.random_int(1,180001) )
    fake_data_reserva["idCliente"].append( fake.random_int(1,180001) )
    duracion = fake.random_int(1,30)
    fake_data_reserva["duracion"].append( duracion )
    fake_data_reserva["fechaInicio"].append( fake.date_time_between(start_date = "-4y", end_date = "now") )
    fake_data_reserva["fechaFinal"].append( fake.date_time_between(start_date = fake_data_reserva["fechaInicio"][-1], end_date = fake.date_time_between(start_date = fake_data_reserva["fechaInicio"][-1], end_date = "+30d") ))
    fake_data_reserva["costoTotal"].append( duracion * fake.random_int(20000,2000000))
    fake_data_reserva["estado"].append( fake.estado_reserva() )
    fake_data_reserva["numPersonas"].append( fake.random_int(1,10) )
    fake_data_reserva["idReservaColectiva"].append( fake.random_int(1,180001) )

df_fake_data_reserva = pd.DataFrame(fake_data_reserva)
df_fake_data_reserva.to_csv("csv loader/reservas.csv", index=False)

#Provider para servicios
reserva_index_provider = DynamicProvider(
     provider_name="idReserva",
     elements=reserva_index
)
