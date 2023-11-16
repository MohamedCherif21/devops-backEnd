FROM openjdk:8-jdk-alpine
EXPOSE 8088

#ADD target/CoCoMarket-0.0.1.jar /app/CoCoMarket.jar
ADD http://localhost:8081/repository/maven-releases/com/example/CoCoMarket/0.0.1/CoCoMarket-0.0.1.jar /app/CoCoMarket.jar
ENTRYPOINT ["java","-jar","/CoCoMarket-0.0.1.jar"]