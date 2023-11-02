FROM openjdk:8-jdk-alpine

  # Définissez le répertoire de travail dans le conteneur
WORKDIR /app

  # Copiez le JAR de votre application dans le conteneur
COPY target/CoCoMarket-0.0.1.jar app.jar

  # Exécutez l'application Spring Boot au démarrage
CMD ["java", "-jar", "app.jar"]