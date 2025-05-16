FROM openjdk:8-jre-alpine

WORKDIR /app
COPY target/ticketsystem-0.0.1-SNAPSHOT.jar app.jar

# Variables de entorno configurables
ENV SPRING_DATASOURCE_URL=jdbc:sqlserver://db:1433;databaseName=ticket_db
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=12345678

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]