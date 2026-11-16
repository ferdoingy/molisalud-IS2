-- Molisalud: esquema PostgreSQL generado desde 'ER - modelo físico' (Sistema_Molisalud_v21.mdj)
-- La base de datos la crea Render; ejecutar este script ya conectado a ella.
-- Las llaves primarias usan SERIAL (autoincremental).

CREATE TABLE FichaPaciente (
    idPaciente SERIAL PRIMARY KEY,
    dni VARCHAR(8) NOT NULL UNIQUE,
    nombres VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    telefono VARCHAR(255),
    correo VARCHAR(255),
    direccion VARCHAR(255)
);

CREATE TABLE Medico (
    idMedico SERIAL PRIMARY KEY,
    nombres VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    especialidad VARCHAR(255) NOT NULL
);

CREATE TABLE TarifarioServicio (
    idServicio SERIAL PRIMARY KEY,
    nombreServicio VARCHAR(255) NOT NULL,
    precio DECIMAL(12,2) NOT NULL
);

CREATE TABLE Usuario (
    idUsuario SERIAL PRIMARY KEY,
    nombreUsuario VARCHAR(255) NOT NULL UNIQUE,
    "contraseña" VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL
);

CREATE TABLE HistoriaClinica (
    idHistoria SERIAL PRIMARY KEY,
    idPaciente INTEGER NOT NULL UNIQUE,
    fecha DATE NOT NULL,
    antecedentes VARCHAR(255),
    CONSTRAINT fk_historiaclinica_idpaciente FOREIGN KEY (idPaciente) REFERENCES FichaPaciente(idPaciente)
);

CREATE TABLE HorarioMedico (
    idHorario SERIAL PRIMARY KEY,
    idMedico INTEGER NOT NULL,
    diaSemana VARCHAR(255) NOT NULL,
    horaInicio TIME NOT NULL,
    horaFin TIME NOT NULL,
    estado VARCHAR(255) NOT NULL,
    CONSTRAINT fk_horariomedico_idmedico FOREIGN KEY (idMedico) REFERENCES Medico(idMedico)
);

CREATE TABLE SolicitudCita (
    idSolicitud SERIAL PRIMARY KEY,
    idPaciente INTEGER NOT NULL,
    fechaSolicitud DATE NOT NULL,
    fechaEscogida DATE NOT NULL,
    horaEscogida TIME NOT NULL,
    motivoConsulta VARCHAR(255) NOT NULL,
    estado VARCHAR(255) NOT NULL,
    CONSTRAINT fk_solicitudcita_idpaciente FOREIGN KEY (idPaciente) REFERENCES FichaPaciente(idPaciente)
);

CREATE TABLE CitaMedica (
    idReserva SERIAL PRIMARY KEY,
    idHorario INTEGER NOT NULL,
    idSolicitud INTEGER NOT NULL UNIQUE,
    fechaReserva DATE NOT NULL,
    hora TIME NOT NULL,
    estado VARCHAR(255) NOT NULL,
    CONSTRAINT fk_citamedica_idhorario FOREIGN KEY (idHorario) REFERENCES HorarioMedico(idHorario),
    CONSTRAINT fk_citamedica_idsolicitud FOREIGN KEY (idSolicitud) REFERENCES SolicitudCita(idSolicitud)
);

CREATE TABLE FichaTriaje (
    idTriaje SERIAL PRIMARY KEY,
    idHistoria INTEGER NOT NULL,
    idCita INTEGER NOT NULL UNIQUE,
    fecha TIMESTAMP NOT NULL,
    temperatura DECIMAL(12,2) NOT NULL,
    peso DECIMAL(12,2) NOT NULL,
    talla DECIMAL(12,2) NOT NULL,
    presionArterial VARCHAR(255) NOT NULL,
    CONSTRAINT fk_fichatriaje_idhistoria FOREIGN KEY (idHistoria) REFERENCES HistoriaClinica(idHistoria),
    CONSTRAINT fk_fichatriaje_idcita FOREIGN KEY (idCita) REFERENCES CitaMedica(idReserva)
);

CREATE TABLE Pago (
    idPago SERIAL PRIMARY KEY,
    idCita INTEGER NOT NULL UNIQUE,
    idServicio INTEGER NOT NULL,
    fechaPago TIMESTAMP NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    metodoPago VARCHAR(255) NOT NULL,
    CONSTRAINT fk_pago_idcita FOREIGN KEY (idCita) REFERENCES CitaMedica(idReserva),
    CONSTRAINT fk_pago_idservicio FOREIGN KEY (idServicio) REFERENCES TarifarioServicio(idServicio)
);

CREATE TABLE RegistroConsultaMedica (
    idConsulta SERIAL PRIMARY KEY,
    idHistoria INTEGER NOT NULL,
    idCita INTEGER NOT NULL UNIQUE,
    idMedico INTEGER NOT NULL,
    fecha TIMESTAMP NOT NULL,
    motivoConsulta VARCHAR(255) NOT NULL,
    observaciones VARCHAR(255),
    CONSTRAINT fk_registroconsultamedica_idhistoria FOREIGN KEY (idHistoria) REFERENCES HistoriaClinica(idHistoria),
    CONSTRAINT fk_registroconsultamedica_idcita FOREIGN KEY (idCita) REFERENCES CitaMedica(idReserva),
    CONSTRAINT fk_registroconsultamedica_idmedico FOREIGN KEY (idMedico) REFERENCES Medico(idMedico)
);

CREATE TABLE ComprobantePago (
    idComprobante SERIAL PRIMARY KEY,
    idPago INTEGER NOT NULL UNIQUE,
    fechaEmision DATE NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    total DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_comprobantepago_idpago FOREIGN KEY (idPago) REFERENCES Pago(idPago)
);

CREATE TABLE Diagnostico (
    idDiagnostico SERIAL PRIMARY KEY,
    idConsulta INTEGER NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    CONSTRAINT fk_diagnostico_idconsulta FOREIGN KEY (idConsulta) REFERENCES RegistroConsultaMedica(idConsulta)
);

CREATE TABLE Receta (
    idReceta SERIAL PRIMARY KEY,
    idConsulta INTEGER NOT NULL,
    fecha DATE NOT NULL,
    indicaciones VARCHAR(255) NOT NULL,
    CONSTRAINT fk_receta_idconsulta FOREIGN KEY (idConsulta) REFERENCES RegistroConsultaMedica(idConsulta)
);
