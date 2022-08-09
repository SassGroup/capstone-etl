FROM openjdk:11-jre-slim-buster

COPY target/idfc-loan-etl-service-0.1.jar loan-application-etl-service-0.1.jar

ENTRYPOINT ["java","-Dmicronaut.environments=okteto","-jar","-Dmicronaut.server.port=8095","/loan-application-etl-service-0.1.jar"]
