# ETAPA 1: Construcción (Descarga dependencias y compila el .jar)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Compilamos omitiendo tests para que sea más rápido
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Toma el .jar y levanta el servidor)
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copiamos el .jar que se acaba de crear en la etapa 1
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]