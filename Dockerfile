# Usamos directamente la imagen de ejecución
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiamos el .jar que TU generaste con .\mvnw clean package
COPY target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]