# ETAPA 1: Compilación (Build)
# Usamos una imagen de Maven para compilar el código
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copiamos los archivos del proyecto (pom.xml y la carpeta src)
COPY . .

# Ejecutamos el comando de empaquetado (como hacías en tu PC)
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Run)
# Usamos una imagen más ligera solo con Java para correr la app
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiamos el .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]