-- Creacion del secuenciador
create sequence alohaandes_sequence;

-- Creacion de la tabla OPERADOR y especificacion de sus restricciones
CREATE TABLE A_OPERADOR
   (IDOPERADOR NUMBER, 
	TELEFONO VARCHAR2(255 BYTE) NOT NULL,
    TIPOVINCULACION VARCHAR2(255 BYTE) NOT NULL,
	CONSTRAINT A_OPERADOR_PK PRIMARY KEY (IDOPERADOR));
	
ALTER TABLE A_OPERADOR
	ADD CONSTRAINT UN_OPERADOR_TELEFONO
	UNIQUE (TELEFONO)
ENABLE;


-- Creacion de la tabla PROPIETARIO y especificacion de sus restricciones
CREATE TABLE A_PROPIETARIO
    (IDOPERADOR NUMBER, 
    IDENTIFICACION VARCHAR2(255 BYTE) NOT NULL,
    TIPOIDENTIFICACION VARCHAR2(20 BYTE) NOT NULL,
    NOMBREPROPIETARIO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT A_PROPIETARIO_PK PRIMARY KEY (IDOPERADOR)); 

ALTER TABLE A_PROPIETARIO
ADD CONSTRAINT FK_P_OPERADOR
    FOREIGN KEY (IDOPERADOR) REFERENCES A_OPERADOR(IDOPERADOR) 
ENABLE;

ALTER TABLE A_PROPIETARIO
	ADD CONSTRAINT UN_PROPIETARIO_IDENTIFICACION
	UNIQUE (IDENTIFICACION)
ENABLE;

ALTER TABLE A_PROPIETARIO
	ADD CONSTRAINT CK_P_TIPOIDENTIFICACION
	CHECK (TIPOIDENTIFICACION IN ('CC', 'TI', 'PA'))
ENABLE;


-- Creacion de la tabla EMPRESA y especificacion de sus restricciones
CREATE TABLE A_EMPRESA
    (IDOPERADOR NUMBER,
    NIT VARCHAR2(255 BYTE) NOT NULL,
    NOMBREEMPRESA VARCHAR2(255 BYTE) NOT NULL,
   CONSTRAINT A_EMPRESA_PK PRIMARY KEY (IDOPERADOR));

ALTER TABLE A_EMPRESA
ADD CONSTRAINT FK_E_OPERADOR
    FOREIGN KEY (IDOPERADOR) REFERENCES A_OPERADOR(IDOPERADOR)
ENABLE;

ALTER TABLE A_EMPRESA
	ADD CONSTRAINT UN_EMPRESA_NIT
	UNIQUE (NIT)
ENABLE;


-- Creacion de la tabla ALOJAMIENTO y especificacion de sus restricciones
CREATE TABLE A_ALOJAMIENTO
    (IDALOJAMIENTO NUMBER,
    NOMBRE VARCHAR2(255 BYTE) NOT NULL,
    CAPACIDAD NUMBER NOT NULL,
    UBICACION VARCHAR2(255 BYTE) NOT NULL,
    TAMANO NUMBER NOT NULL,
    PRECIONOCHE NUMBER NOT NULL,
    OCUPACIONACTUAL NUMBER NOT NULL,
    NUMRESERVAS NUMBER NOT NULL,
    IDOPERADOR NUMBER,
    CONSTRAINT A_ALOJAMIENTO_PK PRIMARY KEY (IDALOJAMIENTO));

ALTER TABLE A_ALOJAMIENTO
	ADD CONSTRAINT CK_CAPACIDAD
	CHECK (CAPACIDAD > 0)
ENABLE;

ALTER TABLE A_ALOJAMIENTO
	ADD CONSTRAINT CK_TAMANO
	CHECK (TAMANO > 0)
ENABLE;

ALTER TABLE A_ALOJAMIENTO
	ADD CONSTRAINT CK_PRECIONOCHE
	CHECK (PRECIONOCHE > 0)
ENABLE;

ALTER TABLE A_ALOJAMIENTO
	ADD CONSTRAINT CK_OCUPACIONACTUAL
	CHECK (OCUPACIONACTUAL >= 0 AND OCUPACIONACTUAL <= CAPACIDAD)
ENABLE;

ALTER TABLE A_ALOJAMIENTO
	ADD CONSTRAINT CK_NUMRESERVAS
	CHECK (NUMRESERVAS >= 0)
ENABLE;

ALTER TABLE A_ALOJAMIENTO
ADD CONSTRAINT FK_A_OPERADOR
    FOREIGN KEY (IDOPERADOR) REFERENCES A_OPERADOR(IDOPERADOR)
ENABLE;


-- Creacion de la tabla VIVIENDAUNIVERSITARIA y especificacion de sus restricciones
CREATE TABLE A_VIVIENDAUNIVERSITARIA
    (IDALOJAMIENTO NUMBER,
     TIPOHABITACION VARCHAR2(20 BYTE) NOT NULL,
     CONSTRAINT A_VIVIENDAUNIVERSITARIA_PK PRIMARY KEY (IDALOJAMIENTO));

ALTER TABLE A_VIVIENDAUNIVERSITARIA
ADD CONSTRAINT FK_VU_ALOJAMIENTO
    FOREIGN KEY (IDALOJAMIENTO) REFERENCES A_ALOJAMIENTO(IDALOJAMIENTO)
ENABLE;

ALTER TABLE A_VIVIENDAUNIVERSITARIA
	ADD CONSTRAINT CK_VU_TIPOHABITACION
	CHECK (TIPOHABITACION IN ('Compartida', 'Individual'))
ENABLE;


-- Creacion de la tabla HABITACIONVIVIENDA y especificacion de sus restricciones
CREATE TABLE A_HABITACIONVIVIENDA
    (IDALOJAMIENTO NUMBER,
     TIPOBANO VARCHAR2(20 BYTE) NOT NULL,
     TIPOHABITACION VARCHAR2(20 BYTE) NOT NULL,
     CONSTRAINT A_HABITACIONVIVIENDA_PK PRIMARY KEY (IDALOJAMIENTO));

ALTER TABLE A_HABITACIONVIVIENDA
ADD CONSTRAINT FK_HV_ALOJAMIENTO
    FOREIGN KEY (IDALOJAMIENTO) REFERENCES A_ALOJAMIENTO(IDALOJAMIENTO)
ENABLE;

ALTER TABLE A_HABITACIONVIVIENDA
	ADD CONSTRAINT CK_TIPOBANO
	CHECK (TIPOBANO IN ('Privado', 'Publico'))
ENABLE;

ALTER TABLE A_HABITACIONVIVIENDA
	ADD CONSTRAINT CK_HV_TIPOHABITACION
	CHECK (TIPOHABITACION IN ('Compartida', 'Individual'))
ENABLE;


-- Creacion de la tabla APARTAMENTO y especificacion de sus restricciones
CREATE TABLE A_APARTAMENTO
    (IDALOJAMIENTO NUMBER,
    CONSTRAINT A_APARTAMENTO_PK PRIMARY KEY (IDALOJAMIENTO));

ALTER TABLE A_APARTAMENTO
ADD CONSTRAINT FK_A_ALOJAMIENTO
    FOREIGN KEY (IDALOJAMIENTO) REFERENCES A_ALOJAMIENTO(IDALOJAMIENTO)
ENABLE;


-- Creacion de la tabla HABITACIONHOTEL y especificacion de sus restricciones
CREATE TABLE A_HABITACIONHOTEL
    (IDALOJAMIENTO NUMBER,
     TIPOHABITACION VARCHAR2(20 BYTE) NOT NULL,
     CONSTRAINT A_HABITACIONHOTEL_PK PRIMARY KEY (IDALOJAMIENTO));

ALTER TABLE A_HABITACIONHOTEL
ADD CONSTRAINT FK_HH_ALOJAMIENTO
    FOREIGN KEY (IDALOJAMIENTO) REFERENCES A_ALOJAMIENTO(IDALOJAMIENTO)
ENABLE;

ALTER TABLE A_HABITACIONHOTEL
	ADD CONSTRAINT CK_HH_TIPOHABITACION
	CHECK (TIPOHABITACION IN ('Estandar', 'Semisuites', 'Suites'))
ENABLE;


-- Creacion de la tabla HOSTAL y especificacion de sus restricciones
CREATE TABLE A_HOSTAL
    (IDALOJAMIENTO NUMBER,
     HORARIOAPERTURA VARCHAR2(20 BYTE) NOT NULL,
     HORARIOCIERRE VARCHAR2(20 BYTE) NOT NULL,
     CONSTRAINT A_HOSTAL_PK PRIMARY KEY (IDALOJAMIENTO));

ALTER TABLE A_HOSTAL
ADD CONSTRAINT FK_H_ALOJAMIENTO
    FOREIGN KEY (IDALOJAMIENTO) REFERENCES A_ALOJAMIENTO(IDALOJAMIENTO)
ENABLE;


-- Creacion de la tabla VIVIENDATEMPORAL y especificacion de sus restricciones
CREATE TABLE A_VIVIENDATEMPORAL
    (IDALOJAMIENTO NUMBER,
     NUMHABITACIONES NUMBER NOT NULL,
     CONSTRAINT A_VIVIENDATEMPORAL_PK PRIMARY KEY (IDALOJAMIENTO));

ALTER TABLE A_VIVIENDATEMPORAL
ADD CONSTRAINT FK_VT_ALOJAMIENTO
    FOREIGN KEY (IDALOJAMIENTO) REFERENCES A_ALOJAMIENTO(IDALOJAMIENTO)
ENABLE;

ALTER TABLE A_VIVIENDATEMPORAL
	ADD CONSTRAINT CK_VT_NUMHABITACIONES
	CHECK (NUMHABITACIONES > 0)
ENABLE;


-- Creacion de la tabla CLIENTE y especificacion de sus restricciones
CREATE TABLE A_CLIENTE
    (IDCLIENTE NUMBER,
    TIPOIDENTIFICACION VARCHAR2(20 BYTE) NOT NULL,
    NOMBRECLIENTE VARCHAR2(255 BYTE) NOT NULL,
    FECHANACIMIENTO DATE NOT NULL,
    CONSTRAINT A_CLIENTE_PK PRIMARY KEY (IDCLIENTE));

ALTER TABLE A_CLIENTE
	ADD CONSTRAINT CK_C_TIPOIDENTIFICACION
	CHECK (TIPOIDENTIFICACION IN ('CC', 'TI', 'PA'))
ENABLE;

-- Creacion de la tabla MIEMBROACTIVO y especificacion de sus restricciones
CREATE TABLE A_MIEMBROACTIVO
    (IDMIEMBROACTIVO NUMBER,
    CARNET VARCHAR2(255 BYTE) NOT NULL,
    TIPO VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT A_MIEMBROACTIVO_PK PRIMARY KEY (IDMIEMBROACTIVO));

ALTER TABLE A_MIEMBROACTIVO
ADD CONSTRAINT FK_MA_CLIENTE
    FOREIGN KEY (IDMIEMBROACTIVO) REFERENCES A_CLIENTE(IDCLIENTE)
ENABLE;

ALTER TABLE A_MIEMBROACTIVO
	ADD CONSTRAINT UN_MIEMBROACTIVO_CARNET
	UNIQUE (CARNET)
ENABLE;

ALTER TABLE A_MIEMBROACTIVO
	ADD CONSTRAINT CK_MA_MIEMBROACTIVO
	CHECK (TIPO IN ('Estudiante', 'Profesor visitante', 'Empleado', 'Profesor titular'))
ENABLE;

-- Creacion de la tabla MIEMBROSECUNDARIO y especificacion de sus restricciones
CREATE TABLE A_MIEMBROSECUNDARIO
    (IDMIEMBROSECUNDARIO NUMBER,
    TIPO VARCHAR2(20 BYTE) NOT NULL,
    CONSTRAINT A_MIEMBROSECUNDARIO_PK PRIMARY KEY (IDMIEMBROSECUNDARIO));

ALTER TABLE A_MIEMBROSECUNDARIO
ADD CONSTRAINT FK_MS_CLIENTE
    FOREIGN KEY (IDMIEMBROSECUNDARIO) REFERENCES A_CLIENTE(IDCLIENTE)
ENABLE;

ALTER TABLE A_MIEMBROSECUNDARIO
	ADD CONSTRAINT CK_MS_TIPO
	CHECK (TIPO IN ('Padre', 'Egresado', 'Invitado'))
ENABLE;


-- Creacion de la tabla SERVICIO y especificacion de sus restricciones
CREATE TABLE A_SERVICIO
    (IDSERVICIO NUMBER,
     NOMBRE VARCHAR2(255 BYTE) NOT NULL,
     COSTO NUMBER NOT NULL,
    CONSTRAINT A_SERVICIO_PK PRIMARY KEY (IDSERVICIO));

ALTER TABLE A_SERVICIO
	ADD CONSTRAINT CK_S_COSTO
	CHECK (COSTO >= 0)
ENABLE;

-- Creacion de la tabla DISPONE y especificacion de sus restricciones
CREATE TABLE A_DISPONE
    (IDSERVICIO NUMBER,
     IDALOJAMIENTO NUMBER,
    CONSTRAINT A_DISPONE_PK PRIMARY KEY (IDSERVICIO, IDALOJAMIENTO));

ALTER TABLE A_DISPONE
ADD CONSTRAINT FK_D_SERVICIO
    FOREIGN KEY (IDSERVICIO) REFERENCES A_SERVICIO(IDSERVICIO)
ENABLE;

ALTER TABLE A_DISPONE
ADD CONSTRAINT FK_D_ALOJAMIENTO
    FOREIGN KEY (IDALOJAMIENTO) REFERENCES A_ALOJAMIENTO(IDALOJAMIENTO)
ENABLE;

-- Creacion de la tabla RESERVA y especificacion de sus restricciones
CREATE TABLE A_RESERVA
    (IDRESERVA NUMBER,
    IDALOJAMIENTO NUMBER NOT NULL,
    IDCLIENTE NUMBER NOT NULL,
    DURACION NUMBER NOT NULL,
    FECHAINICIO DATE NOT NULL,
    FECHAFINAL DATE NOT NULL,
    COSTOTOTAL NUMBER NOT NULL,
    ESTADO VARCHAR2(20 BYTE) NOT NULL,
    NUMPERSONAS NUMBER NOT NULL,
    CONSTRAINT A_RESERVA_PK PRIMARY KEY (IDRESERVA));


ALTER TABLE A_RESERVA
ADD CONSTRAINT FK_R_ALOJAMIENTO
    FOREIGN KEY (IDALOJAMIENTO) REFERENCES A_ALOJAMIENTO(IDALOJAMIENTO)
ENABLE;

ALTER TABLE A_RESERVA
ADD CONSTRAINT FK_R_CLIENTE
    FOREIGN KEY (IDCLIENTE) REFERENCES A_CLIENTE(IDCLIENTE)
ENABLE;

ALTER TABLE A_RESERVA
	ADD CONSTRAINT CK_R_DURACION
	CHECK (DURACION > 0)
ENABLE;

ALTER TABLE A_RESERVA
	ADD CONSTRAINT CK_R_COSTOTOTAL
	CHECK (COSTOTOTAL > 0)
ENABLE;

ALTER TABLE A_RESERVA
	ADD CONSTRAINT CK_R_NUMPERSONAS
	CHECK (NUMPERSONAS > 0)
ENABLE;

ALTER TABLE A_RESERVA
	ADD CONSTRAINT CK_R_ESTADO
	CHECK (ESTADO IN ('Cancelada', 'Finalizada', 'Activa'))
ENABLE;











