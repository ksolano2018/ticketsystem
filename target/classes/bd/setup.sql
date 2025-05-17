CREATE DATABASE ticket_db;
GO
USE ticket_db;
GO
-- Aquí tus scripts de creación de tablas
CREATE TABLE tickets (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    titulo NVARCHAR(255) NOT NULL,
    descripcion NVARCHAR(MAX) NOT NULL,
    estado NVARCHAR(20) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    fecha_actualizacion DATETIME,
    fecha_vencimiento DATETIME NOT NULL,
    comentario NVARCHAR(MAX)
GO