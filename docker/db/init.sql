WAITFOR DELAY '00:00:10';

-- Crear base de datos si no existe
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = N'ticket_db')
    BEGIN
        CREATE DATABASE ticket_db;
    END
GO

-- Crear usuario SQL
USE master;
GO
IF NOT EXISTS (SELECT * FROM sys.sql_logins WHERE name = 'user')
    BEGIN
        CREATE LOGIN ticket_user WITH PASSWORD = 'Colombia_2025';
    END
GO

-- Usar base
USE ticket_db;
GO


-- Tabla tickets (en la base ticket_db) si no existe
IF OBJECT_ID('dbo.tickets', 'U') IS NULL
    BEGIN
        CREATE TABLE ticket_db.dbo.tickets (
                                               id BIGINT IDENTITY PRIMARY KEY,
                                               titulo NVARCHAR(255),
                                               descripcion NVARCHAR(MAX),
                                               estado NVARCHAR(20),
                                               fecha_creacion DATETIME,
                                               fecha_actualizacion DATETIME,
                                               fecha_vencimiento DATETIME,
                                               comentario NVARCHAR(MAX)
        );
    END
GO

-- Tabla unresolved_tickets_log (en ticket_db) si no existe
IF OBJECT_ID('dbo.unresolved_tickets_log', 'A') IS NULL
    BEGIN
        CREATE TABLE ticket_db.dbo.unresolved_tickets_log (
                                                              id BIGINT IDENTITY PRIMARY KEY,
                                                              titulo NVARCHAR(255),
                                                              descripcion NVARCHAR(MAX),
                                                              estado NVARCHAR(20),
                                                              fecha_creacion DATETIME,
                                                              fecha_actualizacion DATETIME,
                                                              fecha_vencimiento DATETIME,
                                                              comentario NVARCHAR(MAX)
        );
    END
GO

-- Procedimiento almacenado log_unresolved_tickets (si no existe)
IF OBJECT_ID('dbo.log_unresolved_tickets', 'P') IS NULL
    BEGIN
        EXEC('CREATE PROCEDURE ticket_db.dbo.log_unresolved_tickets AS
    BEGIN
        INSERT INTO ticket_db.dbo.unresolved_tickets_log (
            titulo, descripcion, estado, fecha_creacion, fecha_actualizacion, fecha_vencimiento, comentario
        )
        SELECT titulo, descripcion, estado, fecha_creacion, fecha_actualizacion, fecha_vencimiento, comentario
        FROM ticket_db.dbo.tickets
        WHERE estado != ''Resuelto''
          AND fecha_creacion < DATEADD(day, -30, GETDATE());
    END');
    END
GO