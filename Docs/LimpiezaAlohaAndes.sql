--- Sentencias SQL para la creación del esquema de AlohaAndes
--- Las tablas tienen prefijo A_ para facilitar su acceso desde SQL Developer

-- USO
-- Copie el contenido deseado de este archivo en una pestaña SQL de SQL Developer
-- Ejecutelo como un script - Utilice el botón correspondiente de la pestaña utilizada
    
-- Eliminar todas las tablas de la base de datos
DROP TABLE "A_OPERADOR" CASCADE CONSTRAINTS;
DROP TABLE "A_PROPIETARIO" CASCADE CONSTRAINTS;
DROP TABLE "A_EMPRESA" CASCADE CONSTRAINTS;
DROP TABLE "A_ALOJAMIENTO" CASCADE CONSTRAINTS;
DROP TABLE "A_VIVIENDAUNIVERSITARIA" CASCADE CONSTRAINTS;
DROP TABLE "A_HABITACIONVIVIENDA" CASCADE CONSTRAINTS;
DROP TABLE "A_APARTAMENTO" CASCADE CONSTRAINTS;
DROP TABLE "A_HABITACIONHOTEL" CASCADE CONSTRAINTS;
DROP TABLE "A_HOSTAL" CASCADE CONSTRAINTS;
DROP TABLE "A_VIVIENDATEMPORAL" CASCADE CONSTRAINTS;
DROP TABLE "A_CLIENTE" CASCADE CONSTRAINTS;
DROP TABLE "A_MIEMBROACTIVO" CASCADE CONSTRAINTS;
DROP TABLE "A_MIEMBROSECUNDARIO" CASCADE CONSTRAINTS;
DROP TABLE "A_SERVICIO" CASCADE CONSTRAINTS;
DROP TABLE "A_DISPONE" CASCADE CONSTRAINTS;
DROP TABLE "A_RESERVA" CASCADE CONSTRAINTS;
commit;

-- Eliminar el contenido de todas las tablas de la base de datos (El orden es importante)
delete from a_reserva;
delete from a_dispone;
delete from a_propietario;
delete from a_empresa;
delete from a_viviendaUniversitaria;
delete from a_habitacionVivienda;
delete from a_apartamento;
delete from a_habitacionHotel;
delete from a_hostal;
delete from a_viviendaTemporal;
delete from a_miembroActivo;
delete from a_miembroSecundario;
delete from a_cliente;
delete from a_alojamiento;
delete from a_operador;
delete from a_servicio;
commit;