## Sistema de Gestión de Tickets ##

# Descripción

Sistema backend para la gestión de tickets de soporte técnico, desarrollado con Spring Boot y Java 8, siguiendo los principios de Clean Architecture. Permite crear, visualizar, actualizar y resolver tickets de soporte.
Características

    - Creación de tickets con título, descripción y fecha de vencimiento

    - Seguimiento del estado (Abierto, En Proceso, Cerrado, Resuelto)

    - Búsqueda de tickets no resueltos después de 30 días

    - API RESTful documentada

    - Integración con SQL Server

    - Contenerización con Docker

# Tecnologías

    Backend: Java 8, Spring Boot 2.7

    Base de datos: SQL Server 2019

    Pruebas: JUnit 5, Mockito

    Infraestructura: Docker, Docker Compose

    Gestión de dependencias: Maven
	
# Registro Decisiones

1.	Clean Architecture: Segui los principios de Clean Architecture separando claramente las capas (interfaces, aplicación, dominio, infraestructura) para mantener un bajo acoplamiento y alta cohesión.
2.	Base de Datos: Elegi SQL Server como requerido, con Flyway para migraciones para mantener un control de versiones de la estructura de la base de datos.
3.	Dockerización: Configure contenedores separados para la aplicación y la base de datos, facilitando el despliegue y la portabilidad.
4.	Estados del Ticket: Implemente los estados como un enum en la entidad para garantizar la consistencia y validación.
5.	Validación: Agregue validaciones básicas en los DTOs y en el servicio para garantizar la integridad de los datos.
6.	Procedimiento Almacenado: Implemente la funcionalidad de tickets no resueltos mediante un procedimiento almacenado que se ejecuta diariamente, siguiendo el requerimiento.
7.	Pruebas Unitarias: Implemente pruebas unitarias para el servicio, que es donde reside la lógica principal de la aplicación.


# Requisitos

    Java 8 JDK

    Maven 3.8+

    Docker 20.10+

    SQL Server 2019 (opcional para desarrollo local)

# Instalación

1. Con Docker 

- Clonar el repositorio
git clone https://github.com/ksolano2018/ticketsystem.git
cd ticket-management-system

- Construir y levantar los contenedores
docker-compose up --build

2. Localmente

- Configurar la base de datos (crear database ticket_db)

- Configurar application.properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=ticket_db
spring.datasource.username=sa
spring.datasource.password=12345678

- Compilar y ejecutar
mvn clean package
java -jar target/ticket-management-system-0.0.1-SNAPSHOT.jar

# Uso

Endpoints de la API
Método		Endpoint					Descripción
POST	/api/tickets				Crear un nuevo ticket
GET		/api/tickets				Obtener todos los tickets
GET		/api/tickets/{id}			Obtener un ticket por ID
PATCH	/api/tickets/{id}/status	Actualizar estado de un ticket
GET	/api/tickets/unresolved-old	Tickets no resueltos después de 30 días

# Ejemplos de Requests

1 Crear Tickets

curl -X POST \
  http://localhost:8080/api/tickets \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Error en login",
    "descripcion": "Usuarios no pueden acceder al sistema",
    "fechaVencimiento": "2025-12-31T23:59:59"
  }'
  
2 Actualizar estado:

curl -X PATCH \
  http://localhost:8080/api/tickets/1/status \
  -H "Content-Type: application/json" \
  -d '{
    "estado": "EN_PROCESO",
    "comentario": "Asignado al equipo de soporte"
  }'

# Estructura del Proyecto

src/
├── main/
│   ├── java/
│   │   └── com/example/ticketsystem/
│   │       ├── application/       # Lógica de negocio
│   │       ├── domain/            # Entidades y repositorios
│   │       ├── infrastructure/    # Configuraciones
│   │       ├── interfaces/        # Controllers y DTOs
│   │       └── TicketsystemApplication.java
│   └── resources/
│       ├── application.properties
│       └── db/                    # Scripts SQL
├── test/                          # Pruebas unitarias

# Pruebas

1 Ejecutar todas las pruebas:
mvn test

2 Ejecutar pruebas específicas:
mvn test -Dtest=TicketServiceTest

# Configuración de Docker

1 El sistema está configurado para funcionar con:

    Aplicación: Puerto 8081

    SQL Server: Puerto 1433

2 Variables de entorno importantes:

    SA_PASSWORD: Contraseña para SQL Server

    SPRING_DATASOURCE_URL: jdbc:sqlserver://localhost:1433;databaseName=ticket_db;encrypt=true;trustServerCertificate=true -> (URL de conexión a la BD)

    SPRING_DATASOURCE_USERNAME: sa --> (Usuario de la BD)

    SPRING_DATASOURCE_PASSWORD: 12345678 -> (Contraseña de la BD)
	
	
# Creado Por:

Kevin Leonardo Solano Bravo - kesoba333@gmail.com

Enlace del proyecto: https://github.com/ksolano2018/ticketsystem.git