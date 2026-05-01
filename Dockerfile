# ==========================================
# ETAPA 1: Construcción (Descarga dependencias y compila el .jar)
# ==========================================
FROM maven:3.8.5-openjdk-17 AS build

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el archivo pom.xml y descargamos las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Compilamos el proyecto y generamos el archivo .jar
RUN mvn clean package -DskipTests

# ==========================================
# ETAPA 2: Ejecución (Toma el .jar y levanta el servidor)
# ==========================================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copiamos EXCLUSIVAMENTE el .jar que se creó en la Etapa 1
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Comando para levantar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]