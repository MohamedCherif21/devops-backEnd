FROM openjdk:8-jdk-alpine

EXPOSE 8088

ADD target/CoCoMarket-0.0.1.jar OumaimaMarket.jar

ENTRYPOINT ["java", "-jar", "/OumaimaMarket.jar"]
