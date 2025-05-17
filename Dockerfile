# Usamos imagen oficial de Maven con Java 8
FROM maven:3.8.6-openjdk-8 AS builder

# Instalamos herramientas adicionales si son necesarias
RUN apt-get update && \
    apt-get install -y \
    git \
    && rm -rf /var/lib/apt/lists/*

# Configuramos el directorio de trabajo
WORKDIR /app

# Copiamos solo los archivos necesarios para instalar dependencias primero
COPY pom.xml .
COPY src ./src

# Descargamos dependencias (esto crea una capa cacheable)
RUN mvn dependency:go-offline

# Compilamos la aplicación
RUN mvn clean package -DskipTests

# Imagen final con solo el JAR y Java Runtime
FROM openjdk:8-jre-alpine

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]