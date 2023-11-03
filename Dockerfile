FROM openjdk:8-jdk-alpine

EXPOSE 8088

# Copiez le JAR de votre application dans le conteneur
ADD target/CoCoMarket-0.0.1.jar app.jar

# Exécutez l'application Spring Boot au démarrage
ENTRYPOINT ["java", "-jar", "/app.jar"]