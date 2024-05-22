FROM openjdk:21
COPY "./target/Starship-Api-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


# Construir imagen
# docker build -t app-starship-image:1.1 .

# Ejecutar aplicacion
# docker run -p8080:8080 --name app-starship-container app-starship-image:1.1